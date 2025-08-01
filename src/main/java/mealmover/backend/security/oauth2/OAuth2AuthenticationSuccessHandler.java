package mealmover.backend.security.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.security.JwtService;
import mealmover.backend.security.UserDetailsImpl;
import mealmover.backend.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${spring.application.frontend.url}")
    private String frontendUrl;

    @Value("${application.security.jwt.tokens.access.expiration}")
    private long jwtAccessTokenExpiration;

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) throws IOException {
        // Generate JWT token
        String token = this.jwtService.generateAccessToken(authentication);

        // Create cookie
        ResponseCookie cookie = CookieUtils.createAccessTokenCookie(token, this.jwtAccessTokenExpiration);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // Redirect to frontend with success parameter
        String targetUrl = UriComponentsBuilder.fromUriString(this.frontendUrl)
            .path("/oauth2/redirect")
            .queryParam("success", true)
            .build()
            .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}