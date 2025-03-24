package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.AuthActivateClientRequestDto;
import mealmover.backend.dtos.AuthRegisterClientRequestDto;
import mealmover.backend.dtos.UserChangePassword;
import mealmover.backend.dtos.requests.AuthForgotPasswordRequestDto;
import mealmover.backend.dtos.requests.AuthLoginRequestDto;
import mealmover.backend.dtos.requests.UserChangeEmail;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.enums.Token;
import mealmover.backend.exceptions.BadRequestException;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.exceptions.UnauthorizedException;
import mealmover.backend.mapper.UserMapper;
import mealmover.backend.messages.PendingClientMessages;
import mealmover.backend.messages.UserMessages;
import mealmover.backend.models.UserModel;
import mealmover.backend.security.JwtService;
import mealmover.backend.security.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PendingClientService pendingClientService;

    private final UserMessages userMessages;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final PendingClientMessages pendingClientMessages;
    private final SecurityService securityService;
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final EmailService emailService;

    public void registerClient(AuthRegisterClientRequestDto requestDto) {
        pendingClientService.create(requestDto);
    }

    public void activateClient(AuthActivateClientRequestDto requestDto) {
        pendingClientService.activate(requestDto);
    }

    public String login(AuthLoginRequestDto requestDto) {
        if (this.pendingClientService.existsByEmail(requestDto.getEmail())) {
            throw new ConflictException(pendingClientMessages.activateFirst());
        }

        UserModel userModel = userService.getModelByEmail(requestDto.getEmail());

        if (!this.passwordEncoder.matches(requestDto.getPassword(), userModel.getPassword())) {
            throw new UnauthorizedException(userMessages.invalidCredentials());
        }

        UserDetails userDetails = userMapper.toUserDetails(userModel);

        return jwtService.generateAccessToken(userDetails);
    }

    public UserResponseDto getAuthUser() {
        String email = this.securityService.getLoggedInUserEmail();
        UserModel userModel = userService.getModelByEmail(email);
        return userMapper.toDto(userModel);
    }

    public void forgotPassword(AuthForgotPasswordRequestDto requestDto) {
        String email = requestDto.getEmail();

        UserModel userModel = userService.getModelByEmail(email);

        UserDetails userDetails = userMapper.toUserDetails(userModel);

        String token = jwtService.generateResetPasswordToken(userDetails);

        this.emailService.sendForgotPasswordEmail(email, token);
    }

    public void resetPassword(String token, String newPassword) {
        String email = jwtService.extractEmail(token);

        UserModel userModel = userService.getModelByEmail(email);

        String hashedPassword = this.passwordEncoder.encode(newPassword);

        userModel.setPassword(hashedPassword);

        userService.save(userModel);
    }

    public void changePassword(UserChangePassword requestDto) {
        if (requestDto.getOldPassword().equals(requestDto.getNewPassword())) {
            throw new RuntimeException("New password must be different from the old password");
        }

        if(!requestDto.getConfirmPassword().equals(requestDto.getNewPassword())) {
            throw new RuntimeException("Passwords does not match");
        }

        String email = this.securityService.getLoggedInUserEmail();

        UserModel userModel = userService.getModelByEmail(email);

        if (!this.passwordEncoder.matches(requestDto.getOldPassword(), userModel.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String hashedPassword = this.passwordEncoder.encode(requestDto.getNewPassword());

        userModel.setPassword(hashedPassword);

        userService.save(userModel);
    }

    public void changeEmail(UserChangeEmail requestDto) {

        String email = this.securityService.getLoggedInUserEmail();
        UserModel userModel = userService.getModelByEmail(email);
        userModel.setEmail(requestDto.getNewEmail()); // necesar pt token?

        if (!this.passwordEncoder.matches(requestDto.getPassword(), userModel.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserDetails userDetails = userMapper.toUserDetails(userModel);

        String emailToken = jwtService.generateChangeEmailToken(userDetails);

        this.emailService.sendChangeEmailEmail(requestDto.getNewEmail(), email, emailToken);

    }

    public void confirmChangeEmail(AuthActivateClientRequestDto requestDto) {
        String emailFromToken = jwtService.extractEmail(requestDto.getToken());
        System.out.println(emailFromToken);
        String loggedInUserEmail = this.securityService.getLoggedInUserEmail();
        UserModel userModel = userService.getModelByEmail(loggedInUserEmail); // SAU din reuqestDto?
        System.out.println(userModel.getFirstName());
        userModel.setEmail(emailFromToken);
        userService.save(userModel);
    }




}
