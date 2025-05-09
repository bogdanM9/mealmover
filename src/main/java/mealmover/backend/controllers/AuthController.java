package mealmover.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.AuthActivateClientRequestDto;
import mealmover.backend.dtos.AuthRegisterClientRequestDto;
import mealmover.backend.dtos.requests.AuthLoginRequestDto;
import mealmover.backend.dtos.responses.MessageResponseDto;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.enums.Token;
import mealmover.backend.messages.AuthMessages;
import mealmover.backend.services.AuthService;
import mealmover.backend.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    @Value("${application.security.jwt.tokens.access.expiration}")
    private long jwtAccessTokenExpiration;
    private final AuthService authService;
    private final UserService userService;
    private final AuthMessages messages;

    @PostMapping("/register-client")
    public ResponseEntity<MessageResponseDto> registerClient(
        @Valid @RequestBody AuthRegisterClientRequestDto requestDto
    ) {
        this.authService.registerClient(requestDto);
        MessageResponseDto responseDto = MessageResponseDto.info(messages.registerSuccess());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/activate-client")
    public ResponseEntity<MessageResponseDto> activateClient(
        @Valid @RequestBody AuthActivateClientRequestDto requestDto
    ) {
        this.authService.activateClient(requestDto);
        MessageResponseDto responseDto = MessageResponseDto.success(messages.activateSuccess());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponseDto> login(
        @Valid @RequestBody AuthLoginRequestDto requestDto,
        HttpServletResponse response
    ) {
        String token = this.authService.login(requestDto);

        ResponseCookie cookie = ResponseCookie.from(Token.ACCESS.toString(), token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(jwtAccessTokenExpiration / 1000)
            .sameSite("Lax")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        MessageResponseDto responseDto = MessageResponseDto.success("Login successful.");

        return ResponseEntity.ok(responseDto);
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        UserResponseDto userDto = this.userService.getCurrentUser();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(Token.ACCESS.toString(), "")
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(0)
            .sameSite("Lax")
            .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        MessageResponseDto responseDto = MessageResponseDto.success("Logout successful");
        return ResponseEntity.ok(responseDto);
    }
//
//    @PostMapping("/forgot-password")
//    public ResponseEntity<MessageResponseDto> forgotPassword(@Valid @RequestBody AuthForgotPasswordRequestDto requestDto) {
//        authService.forgotPassword(requestDto);
//        return ResponseEntity.ok(MessageResponseDto.info("Reset password link sent to your email."));
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<MessageResponseDto> resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
//        authService.resetPassword(requestDto.getToken(), requestDto.getPassword());
//        return ResponseEntity.ok(MessageResponseDto.info("Password reset successfully."));
//    }
//
//    @PostMapping("/change-email")
//    public ResponseEntity<String> changeEmail(@Valid @RequestBody UserChangeEmail requestDto) {
//        authService.changeEmail(requestDto);
//        return ResponseEntity.ok("CHANGE-EMAIL");
//    }
//
//    @PostMapping("/confirm-change-email")
//    public ResponseEntity<String> confirmChangeEmail(@RequestBody AuthActivateClientRequestDto token) {
//        authService.confirmChangeEmail(token);
//        return ResponseEntity.ok("CONFIRM-CHANGE-EMAIL");
//    }
//
//    @PostMapping("/change-password")
//    public ResponseEntity<String> changePassword(@RequestBody UserChangePassword requestDto) {
//        authService.changePassword(requestDto);
//        return ResponseEntity.ok("CHANGE-PASSWORD");
//    }
}