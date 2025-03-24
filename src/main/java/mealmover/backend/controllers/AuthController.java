package mealmover.backend.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.AuthActivateClientRequestDto;
import mealmover.backend.dtos.AuthRegisterClientRequestDto;
import mealmover.backend.dtos.UserChangePassword;
import mealmover.backend.dtos.requests.AuthForgotPasswordRequestDto;
import mealmover.backend.dtos.requests.AuthLoginRequestDto;
import mealmover.backend.dtos.requests.ResetPasswordRequestDto;
import mealmover.backend.dtos.requests.UserChangeEmail;
import mealmover.backend.dtos.responses.MessageResponseDto;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.enums.Token;
import mealmover.backend.messages.AuthMessages;
import mealmover.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${application.security.jwt.access-token.expiration}")
    private long accessTokenExpiration;
    private final AuthService authService;
    private final AuthMessages messages;

    @PostMapping("/register-client")
    public ResponseEntity<MessageResponseDto> registerClient(@Valid @RequestBody AuthRegisterClientRequestDto requestDto) {
        authService.registerClient(requestDto);
        MessageResponseDto responseDto = MessageResponseDto.info(messages.registerSuccess());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/activate-client")
    public ResponseEntity<MessageResponseDto> activateClient(@Valid @RequestBody AuthActivateClientRequestDto requestDto) {
        authService.activateClient(requestDto);
        MessageResponseDto responseDto = MessageResponseDto.success(messages.activateSuccess());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponseDto> login(
        @Valid @RequestBody AuthLoginRequestDto requestDto,
        HttpServletResponse response
    ) {
        String token = authService.login(requestDto);

        Cookie accessTokenCookie = new Cookie(Token.ACCESS.toCamelCase(), token);

        accessTokenCookie.setPath("/");
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setMaxAge((int) accessTokenExpiration / 1000);
        accessTokenCookie.setHttpOnly(true);

        response.addCookie(accessTokenCookie);

        MessageResponseDto responseDto = MessageResponseDto.success(messages.loginSuccess());

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getAuthUser() {
        return ResponseEntity.ok(this.authService.getAuthUser());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDto> forgotPassword(@Valid @RequestBody AuthForgotPasswordRequestDto requestDto) {
        authService.forgotPassword(requestDto);
        return ResponseEntity.ok(MessageResponseDto.info("Reset password link sent to your email."));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDto> resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
        authService.resetPassword(requestDto.getToken(), requestDto.getPassword());
        return ResponseEntity.ok(MessageResponseDto.info("Password reset successfully."));
    }

    @PostMapping("/change-email")
    public ResponseEntity<String> changeEmail(@Valid @RequestBody UserChangeEmail requestDto) {
        authService.changeEmail(requestDto);
        return ResponseEntity.ok("CHANGE-EMAIL");
    }

    @PostMapping("/confirm-change-email")
    public ResponseEntity<String> confirmChangeEmail(@RequestBody AuthActivateClientRequestDto token) {
        authService.confirmChangeEmail(token);
        return ResponseEntity.ok("CONFIRM-CHANGE-EMAIL");
    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePassword requestDto) {
        authService.changePassword(requestDto);
        return ResponseEntity.ok("CHANGE-PASSWORD");
    }


}