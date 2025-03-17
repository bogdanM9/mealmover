package mealmover.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mealmover.backend.enums.Severity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class EntryPointAuthentication implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String message = extractMessageFromException(authException);

        Map<String, String> errorResponse = Map.of(
            "message",
            message,
            "severity",
            Severity.ERROR.toLower()
        );

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);
    }

    // TODO - schimba in engleza
    private String extractMessageFromException(AuthenticationException exception) {
        // Check if JWT exception is the cause
        Throwable cause = exception.getCause();
        if (cause instanceof ExpiredJwtException) {
            return "Sesiunea dumneavoastră a expirat. Vă rugăm să vă autentificați din nou.";
        }

        // Check Spring Security exceptions
        if (exception instanceof BadCredentialsException) {
            return "Credențiale invalide.";
        }

        if (exception instanceof InsufficientAuthenticationException) {
            return "Trebuie să fiți autentificat pentru a accesa această resursă.";
        }

        // Look for token expired message in the exception message
        String exceptionMessage = exception.getMessage();

        if (exceptionMessage != null && (exceptionMessage.contains("expired"))) {
            return "Sesiunea dumneavoastră a expirat. Vă rugăm să vă autentificați din nou.";
        }

        return "Eroare de autentificare: " + exception.getMessage();
    }
}