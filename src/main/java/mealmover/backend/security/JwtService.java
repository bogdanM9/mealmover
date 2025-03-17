package mealmover.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import mealmover.backend.enums.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private static final String TOKEN_TYPE_CLAIM = "token_type";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private final Key signingKey;
    private final long accessTokenExpiration;
    private final long resetPasswordTokenExpiration;
    private final long activateClientTokenExpiration;

    public JwtService(
            @Value("${application.security.jwt.secret-key}") String secretKey,
            @Value("${application.security.jwt.access-token.expiration}") long accessExpiration,
            @Value("${application.security.jwt.reset-password-token.expiration}") long resetPasswordExpiration,
            @Value("${application.security.jwt.activate-client-token.expiration}") long activateClientExpiration
    ) {
        this.accessTokenExpiration = accessExpiration;
        this.resetPasswordTokenExpiration = resetPasswordExpiration;
        this.activateClientTokenExpiration = activateClientExpiration;
        this.signingKey = initializeSigningKey(secretKey);
    }

    private Key initializeSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails, String tokenType, long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE_CLAIM, tokenType);
        return buildToken(claims, userDetails, expiration);
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails, Token.ACCESS.toCamelCase(), accessTokenExpiration);
    }

    public String generateActivateClientToken(UserDetails userDetails) {
        return generateToken(userDetails, Token.ACTIVATE_CLIENT.toCamelCase(), activateClientTokenExpiration);
    }

    public String generateResetPasswordToken(UserDetails userDetails) {
        return generateToken(userDetails, Token.RESET_PASSWORD.toCamelCase(), resetPasswordTokenExpiration);
    }

    public String generateChangeEmailToken(UserDetails userDetails) {
        return generateToken(userDetails, Token.CHANGE_EMAIL.toCamelCase(), accessTokenExpiration);
    }


    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(signingKey, SIGNATURE_ALGORITHM)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails, String tokenType) {
        String email = extractEmail(token);
        String type = extractClaim(token, claims -> claims.get(TOKEN_TYPE_CLAIM, String.class));

        return email.equals(userDetails.getUsername()) &&
                type.equals(tokenType) &&
                isTokenNotExpired(token);
    }

    private boolean isTokenNotExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}