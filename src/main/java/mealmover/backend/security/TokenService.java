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
    private final JwtUtils jwtUtils;

    /**
     * Generate a token for account registration confirmation
     */
    public String generateRegistrationClientToken(String email) {
        return this.jwtUtils.generateRegistrationClientToken(email);
    }

    /**
     * Generate a token for password reset
     */
    public String generatePasswordResetToken(String email, UUID userId) {
        return this.jwtUtils.generatePasswordResetToken(email, userId);
    }

    /**
     * Generate a token for access
     */
    public String generateAccessToken(Authentication authentication) {
        return this.jwtUtils.generateAccessToken(authentication);
    }

    /**
     * Validate a registration token
     */
    public String validateRegistrationClientToken(String token) {
        this.jwtUtils.validateToken(token);
        this.jwtUtils.validateTokenType(token, Token.REGISTRATION_CLIENT);
        return this.jwtUtils.getUsernameFromToken(token);
    }

    /**
     * Validate an application token
     */
    public String validateApplicationToken(String token) {
        this.jwtUtils.validateToken(token);
        this.jwtUtils.validateTokenType(token, Token.APPLICATION);
        return this.jwtUtils.getUsernameFromToken(token);
    }

    /**
     * Validate a password reset token
     */
    public void validatePasswordResetToken(String token) {
        this.jwtUtils.validateToken(token);
        this.jwtUtils.validateTokenType(token, Token.PASSWORD_RESET);
    }

    /**
     * Get user ID from token
     */
    public UUID getUserIdFromToken(String token) {
        return this.jwtUtils.getIdFromToken(token);
    }

    /**
     * Get email from token
     */
    public String getEmailFromToken(String token) {
        return this.jwtUtils.getUsernameFromToken(token);
    }

    /**
     * Get token type
     */
    public Token getTokenType(String token) {
        return this.jwtUtils.getTokenType(token);
    }
}