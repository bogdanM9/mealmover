package mealmover.backend.services.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import mealmover.backend.enums.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// Json Web Token
@Service
public class JwtService {
    private static final String TOKEN_TYPE_CLAIM = "token_type";
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
    private final Key signingKey;
    private final long accessExpiration;
    private final long activateExpiration;
    private final long resetPasswordExpiration;

    public JwtService(
         @Value("${application.security.jwt.secret-key}") String secretKey,
         @Value("${application.security.jwt.access-token.expiration}") long accessExpiration,
         @Value("${application.security.jwt.activate-token.expiration}") long activateExpiration,
         @Value("${application.security.jwt.reset-password-token.expiration}") long resetPasswordExpiration
    ) {
        this.accessExpiration = accessExpiration;
        this.activateExpiration = activateExpiration;
        this.resetPasswordExpiration = resetPasswordExpiration;
        this.signingKey = initializeSigningKey(secretKey);
    }

    private Key initializeSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts
            .builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(signingKey, SIGNATURE_ALGORITHM)
            .compact();
    }

    private String generateToken(UserDetails userDetails, String tokenType, long expiration) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE_CLAIM, tokenType);
        return buildToken(claims, userDetails, expiration);
    }

    public String generateActivateToken(UserDetails userDetails) {
        return generateToken(userDetails, Token.ACTIVATE_CLIENT.toCamelCase(), activateExpiration);
    }
}
