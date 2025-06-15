package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import mealmover.backend.constants.PendingClientConstants;
import mealmover.backend.constants.UserConstants;
import mealmover.backend.dtos.UserChangePassword;
import mealmover.backend.dtos.requests.*;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.mapper.ClientMapper;
import mealmover.backend.mapper.PendingClientMapper;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.PendingClientModel;
import mealmover.backend.models.UserModel;
import mealmover.backend.security.JwtUtils;
import mealmover.backend.security.SecurityService;
import mealmover.backend.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final ClientMapper clientMapper;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final PendingClientMapper pendingClientMapper;
    private final PendingClientService pendingClientService;
    private final AuthenticationManager authenticationManager;
    private final SecurityService securityService;

    private final JwtUtils jwtUtils;
    public void registerClient(AuthRegisterClientRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        log.info("Attempting to create an pending client with email: {}", email);

        if(this.userService.existsByEmail(email)) {
            throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        if (this.pendingClientService.existsByEmail(email)) {
            throw new ConflictException(PendingClientConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        PendingClientModel pendingClientModel = this.pendingClientMapper.toModel(requestDto);

        String hashedPassword = this.passwordEncoder.encode(password);

        pendingClientModel.setPassword(hashedPassword);

        String token = this.tokenService.generateRegistrationClientToken(email);

        System.out.println(token);

        this.pendingClientService.create(pendingClientModel);

        this.emailService.sendActivateClientAccountEmail(email, token);
    }

    public void activateClient(AuthActivateClientRequestDto requestDto) {
        String token = requestDto.getToken();

        String email = this.tokenService.validateRegistrationToken(token);

        PendingClientModel pendingPatientModel = this.pendingClientService.getModelByEmail(email);

        ClientModel clientModel = this.clientMapper.toModel(pendingPatientModel);

        this.clientService.create(clientModel);

        this.pendingClientService.deleteById(pendingPatientModel.getId());
    }

    public String login(AuthLoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            requestDto.getEmail(),
            requestDto.getPassword()
        );

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return this.tokenService.generateAccessToken(authentication);
    }

    public void forgotPassword(AuthForgotPasswordRequestDto requestDto) {
        String email = requestDto.getEmail();

        log.info("Attempting to reset password for email: {}", email);

        UserModel userModel = this.userService.getModelByEmail(email);

        UUID userId = userModel.getId();

        String token = this.tokenService.generateResetPasswordToken(email, userId);

        System.out.println(token);

        this.emailService.sendForgotPasswordEmail(email, token);

        log.info("Successfully sent reset password email to: {}", email);
    }

    public void resetPassword(AuthResetPasswordRequestDto requestDto) {
        String token = requestDto.getToken();
        String newPassword = requestDto.getNewPassword();

        String email = this.tokenService.validateResetPasswordToken(token);

        UserModel userModel = this.userService.getModelByEmail(email);

        String hashedPassword = this.passwordEncoder.encode(newPassword);

        userModel.setPassword(hashedPassword);

        this.userService.create(userModel);
    }


    public void changeEmail(UserChangeEmail requestDto) {
        String newEmail = requestDto.getNewEmail();

        String password = requestDto.getPassword();

        UserModel userModel = this.securityService.getModelCurrentUser();

        if (!this.passwordEncoder.matches(password, userModel.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        String emailToken = this.jwtUtils.generateChangeEmailToken(newEmail);
        System.out.println(emailToken);
        this.emailService.sendChangeEmail(userModel.getEmail(), newEmail,emailToken);

    }

        public void confirmChangeEmail(AuthActivateClientRequestDto requestDto) {
        String emailFromToken = jwtUtils.extractEmail(requestDto.getToken());
        System.out.println("Email from token ESTE: " + emailFromToken);
        //Aici posibil sa fie o problema cu userModel, daca nu e logat
        UserModel userModel = this.securityService.getModelCurrentUser();
        userModel.setEmail(emailFromToken);
        userService.save(userModel);
    }




    public void changePassword(UserChangePassword requestDto) {
        if (requestDto.getOldPassword().equals(requestDto.getNewPassword())) {
            throw new RuntimeException("New password must be different from the old password");
        }

        if(!requestDto.getConfirmPassword().equals(requestDto.getNewPassword())) {
            throw new RuntimeException("Passwords does not match");
        }

        UserModel userModel = this.securityService.getModelCurrentUser();

        if (!this.passwordEncoder.matches(requestDto.getOldPassword(), userModel.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String hashedPassword = this.passwordEncoder.encode(requestDto.getNewPassword());

        userModel.setPassword(hashedPassword);

        userService.save(userModel);
    }


}