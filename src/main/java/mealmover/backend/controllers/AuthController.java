package mealmover.backend.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.constants.AuthConstants;
import mealmover.backend.dtos.UserChangePassword;
import mealmover.backend.dtos.requests.*;
import mealmover.backend.dtos.responses.MessageResponseDto;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.security.SecurityService;
import mealmover.backend.services.AuthService;
import mealmover.backend.utils.CookieUtils;
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
    private final SecurityService securityService;

    @PostMapping("/register")
    public ResponseEntity<MessageResponseDto> register(
        @Valid @RequestBody AuthRegisterRequestDto requestDto
    ) {
        this.authService.register(requestDto);
        MessageResponseDto responseDto = MessageResponseDto.info(AuthConstants.REGISTER_SUCCESS);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/activate")
    public ResponseEntity<MessageResponseDto> activate(
        @Valid @RequestBody AuthActivateRequestDto requestDto
    ) {
        this.authService.activate(requestDto);
        MessageResponseDto responseDto = MessageResponseDto.success(AuthConstants.ACTIVATE_SUCCESS);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<MessageResponseDto> login(
        @Valid @RequestBody AuthLoginRequestDto requestDto,
        HttpServletResponse response
    ) {
        String token = this.authService.login(requestDto);

        ResponseCookie cookie = CookieUtils.createAccessTokenCookie(token, this.jwtAccessTokenExpiration);

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        MessageResponseDto responseDto = MessageResponseDto.success(AuthConstants.LOGIN_SUCCESS);

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        UserResponseDto userDto = this.securityService.getDtoCurrentUser();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponseDto> logout(
        HttpServletResponse response
    ) {
        CookieUtils.clearAccessTokenCookie(response);
        MessageResponseDto responseDto = MessageResponseDto.success(AuthConstants.LOGOUT_SUCCESS);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponseDto> forgotPassword(
        @Valid @RequestBody AuthForgotPasswordRequestDto requestDto
    ) {
        this.authService.forgotPassword(requestDto);
        return ResponseEntity.ok(MessageResponseDto.info(AuthConstants.FORGOT_PASSWORD));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponseDto> resetPassword(
        @Valid @RequestBody AuthResetPasswordRequestDto requestDto
    ) {
        this.authService.resetPassword(requestDto);
        return ResponseEntity.ok(MessageResponseDto.success(AuthConstants.RESET_PASSWORD));
    }
//
//    @PostMapping("/change-email")
//    public ResponseEntity<String> changeEmail(@Valid @RequestBody UserChangeEmail requestDto) {
//        authService.changeEmail(requestDto);
//        return ResponseEntity.ok("CHANGE-EMAIL");
//    }
//
//    @PostMapping("/confirm-change-email")
//    public ResponseEntity<String> confirmChangeEmail(@RequestBody AuthActivateRequestDto token) {
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