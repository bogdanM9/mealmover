package mealmover.backend.security;

import lombok.RequiredArgsConstructor;
import mealmover.backend.enums.Token;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service for handling token-related operations across the application
 */
@Service
@RequiredArgsConstructor
public class TokenService {
    private final JwtService jwtService;

    /**
     * Generate a token for access
     */
    public String generateAccessToken(Authentication authentication) {
        return this.jwtService.generateAccessToken(authentication);
    }

    /**
     * Generate a token for activate
     */
    public String generateActivateToken(String email) {
        return this.jwtService.generateActivateToken(email);
    }

    /**
     * Generate a token for password reset
     */
    public String generateResetPasswordToken(String email, UUID userId) {
        return this.jwtService.generateResetPasswordToken(email, userId);
    }

    /**
     * Validate a registration token
     */
    public String validateActivateToken(String token) {
        this.jwtService.validateToken(token);
        this.jwtService.validateTokenType(token, Token.ACTIVATE);
        return this.jwtService.getUsernameFromToken(token);
    }

    /**
     * Validate a password reset token
     */
    public String validateResetPasswordToken(String token) {
        this.jwtService.validateToken(token);
        this.jwtService.validateTokenType(token, Token.RESET_PASSWORD);
        return this.jwtService.getUsernameFromToken(token);
    }

    /**
     * Get token type
     */
    public Token getTokenType(String token) {
        return this.jwtService.getTokenType(token);
    }
}