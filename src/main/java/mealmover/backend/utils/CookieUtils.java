package mealmover.backend.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mealmover.backend.enums.Token;
import mealmover.backend.enums.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;

public class CookieUtils {
    public static ResponseCookie createAccessTokenCookie(String tokenValue, long expirationSeconds) {
        return ResponseCookie.from(Token.ACCESS.toString(), tokenValue)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(expirationSeconds / 1000)
            .sameSite("Lax")
            .build();
    }

    public static String extractCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    public static String extractAccessTokenCookie(HttpServletRequest request) {
        return CookieUtils.extractCookie(request, Token.ACCESS.toString());
    }

    public static void clearTokenCookie(Token token, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(token.toString(), "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("Lax")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    public static void clearAccessTokenCookie(HttpServletResponse response) {
        clearTokenCookie(Token.ACCESS, response);
    }
}