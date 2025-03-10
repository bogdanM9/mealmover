package mealmover.backend.middlewares;

import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.exceptions.UnauthorizedException;
import mealmover.backend.dtos.responses.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorMiddleware {
    private static final Logger logger = LoggerFactory.getLogger(ErrorMiddleware.class);

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException e) {
        logger.error("Not found exception: ", e);
        ErrorResponseDto responsePayload = new ErrorResponseDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ErrorResponseDto> handleConflictException(ConflictException e){
        logger.error("Conflict Exception: ", e);
        ErrorResponseDto responsePayload = new ErrorResponseDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responsePayload);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorResponseDto> handleUnauthorizedException(UnauthorizedException e){
        logger.error("Unauthorized Exception: ", e);
        ErrorResponseDto responsePayload = new ErrorResponseDto(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responsePayload);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        logger.error("Validation exception:", e);

        String message = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse("Validation failed");

        ErrorResponseDto responsePayload = new ErrorResponseDto(message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayload);
    }
}
