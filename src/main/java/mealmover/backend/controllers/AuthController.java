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
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.enums.Token;
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

    @PostMapping("/register-client")
    public ResponseEntity<String> registerClient(@Valid @RequestBody AuthRegisterClientRequestDto requestDto) {
        authService.registerClient(requestDto);
        return ResponseEntity.ok("CREATE LOGIN");
    }

    @PostMapping("/activate-client")
    public ResponseEntity<String> activateClient(@Valid @RequestBody AuthActivateClientRequestDto requestDto) {
        authService.activateClient(requestDto);
        return ResponseEntity.ok("ACTIVATE LOGIN");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
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

        return ResponseEntity.ok("LOGIN");
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponseDto> getAuthUser() {
        return ResponseEntity.ok(this.authService.getAuthUser());
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody AuthForgotPasswordRequestDto requestDto) {
        authService.forgotPassword(requestDto);
        return ResponseEntity.ok("FORGOT-EMAIL-PASSWORD SENT");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequestDto requestDto) {
        authService.resetPassword(requestDto.getToken(), requestDto.getPassword());
        return ResponseEntity.ok("RESET-PASSWORD");
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