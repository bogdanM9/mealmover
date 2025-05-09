package mealmover.backend.middlewares;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.responses.ErrorResponseDto;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for JWT-related errors
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtErrorMiddleware {
    /**
     * Handle expired JWT tokens
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleExpiredJwtException(ExpiredJwtException ex) {
        log.error("JWT token is expired: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto("JWT token has expired");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Handle malformed JWT tokens
     */
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleMalformedJwtException(MalformedJwtException ex) {
        log.error("Invalid JWT token: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto("Invalid JWT token format");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Handle JWT signature/security issues
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponseDto> handleSecurityException(SecurityException ex) {
        log.error("Invalid JWT signature: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto("Invalid JWT signature");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Handle unsupported JWT tokens
     */
    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<ErrorResponseDto> handleUnsupportedJwtException(UnsupportedJwtException ex) {
        log.error("JWT token is unsupported: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto("Unsupported JWT token");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Handle other JWT exceptions
     */
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponseDto> handleJwtException(JwtException ex) {
        log.error("JWT token error: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    /**
     * Handle illegal argument exceptions related to JWT
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("JWT claims string is empty: {}", ex.getMessage());
        ErrorResponseDto errorResponse = new ErrorResponseDto("JWT claims string is empty");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
}