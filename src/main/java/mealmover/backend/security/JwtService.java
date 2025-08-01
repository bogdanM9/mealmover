package mealmover.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import mealmover.backend.enums.Token;
import mealmover.backend.security.oauth2.OAuth2UserImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtService {
    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    @Value("${application.security.jwt.tokens.access.expiration}")
    private int jwtAccessTokenExpiration;

    @Value("${application.security.jwt.tokens.activate.expiration}")
    private int jwtActivateTokenExpiration;

    @Value("${application.security.jwt.tokens.reset-password.expiration}")
    private int jwtResetPasswordTokenExpiration;

    /**
     * Get the signing key for JWT operations
     */
    private Key getSigningKey() {
        byte[] keyBytes = jwtSecret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract token type from token
     */
    public Token getTokenType(String token) {
        String typeStr = getClaimsFromToken(token).get("type", String.class);
        return Token.valueOf(typeStr);
    }

    /**
     * Core method to generate a token with given claims and expiration
     */
    private String generateToken(Map<String, Object> claims, String subject, int expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    public String generateAccessToken(Authentication authentication) {
        UserDetailsImpl user = resolveUserDetails(authentication);

        Map<String, Object> claims = buildAccessClaims(user);

        return generateToken(claims, user.getUsername(), jwtAccessTokenExpiration);
    }

    private UserDetailsImpl resolveUserDetails(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2UserImpl oauth) {
            return oauth.getUserDetails();
        }
        if (principal instanceof UserDetailsImpl user) {
            return user;
        }
        throw new IllegalArgumentException(
            "Unsupported principal type: " + principal.getClass().getName()
        );
    }

    private Map<String, Object> buildAccessClaims(UserDetailsImpl user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("type", Token.ACCESS.name());
        claims.put("roles", user.getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))
        );
        return claims;
    }

    /**
     * Extract username from token
     */
    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    /**
     * Generate token for registration confirmation
     */
    public String generateActivateToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", Token.ACTIVATE.name());
        return generateToken(claims, email, jwtActivateTokenExpiration);
    }

    /**
     * Generate token for password reset
     */
    public String generateResetPasswordToken(String email, UUID userId) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("id", userId);
        claims.put("type", Token.RESET_PASSWORD.name());

        return generateToken(claims, email, jwtResetPasswordTokenExpiration);
    }

    /**
     * Extract all claims from token (throws exceptions if invalid)
     */
    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    /**
     * Validate token without catching exceptions
     */
    public void validateToken(String token) {
        Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token);
    }

    public String extractSubject(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * Validate token and check if it matches the expected type
     */
    public void validateTokenType(String token, Token expectedType) {
        Claims claims = getClaimsFromToken(token);
        String typeStr = claims.get("type", String.class);
        Token actualType = Token.valueOf(typeStr);

        if (actualType != expectedType) {
            throw new JwtException("Invalid token type: expected " + expectedType + " but found " + actualType);
        }
    }
}