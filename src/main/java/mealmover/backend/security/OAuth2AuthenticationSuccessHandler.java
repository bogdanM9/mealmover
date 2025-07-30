package mealmover.backend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${spring.application.frontend.url}")
    private String frontendUrl;

    @Value("${application.security.jwt.tokens.access.expiration}")
    private long jwtAccessTokenExpiration;

    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
                                        Authentication authentication) throws IOException {
        
        log.info("OAuth2 authentication successful for user: {}", authentication.getName());
        
        // Generate JWT token for the authenticated user
        String token = tokenService.generateAccessToken(authentication);
        
        // Set JWT token as cookie
        ResponseCookie cookie = CookieUtils.createAccessTokenCookie(token, jwtAccessTokenExpiration);
        response.addHeader("Set-Cookie", cookie.toString());
        
        // Redirect to frontend
        String targetUrl = frontendUrl + "/auth/oauth2/success";
        
        log.info("Redirecting to: {}", targetUrl);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}