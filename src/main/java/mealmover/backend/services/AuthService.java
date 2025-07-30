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
import mealmover.backend.security.JwtService;
import mealmover.backend.security.SecurityService;
import mealmover.backend.security.TokenService;
import mealmover.backend.services.data.PendingClientDataService;
import mealmover.backend.services.data.UserDataService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final ClientMapper clientMapper;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final UserDataService userDataService;
    private final PendingClientMapper pendingClientMapper;
    private final AuthenticationManager authenticationManager;
    private final PendingClientDataService pendingClientDataService;

    @Transactional
    public void register(AuthRegisterRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();
        String phoneNumber = requestDto.getPhoneNumber();

        log.info("Attempting to create an pending client with email: {}", email);

        if(this.userDataService.existsByEmail(email)) {
            throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        if(this.userDataService.existsByPhoneNumber(phoneNumber)) {
            throw new ConflictException(UserConstants.ALREADY_EXISTS_BY_PHONE_NUMBER);
        }

        if (this.pendingClientDataService.existsByEmail(email)) {
            throw new ConflictException(PendingClientConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        if(this.pendingClientDataService.existsByPhoneNumber(phoneNumber)) {
            throw new ConflictException(PendingClientConstants.ALREADY_EXISTS_BY_PHONE_NUMBER);
        }

        PendingClientModel pendingClientModel = this.pendingClientMapper.toModel(requestDto);

        String hashedPassword = this.passwordEncoder.encode(password);

        pendingClientModel.setPassword(hashedPassword);

        String token = this.tokenService.generateActivateToken(email);

        System.out.println(token);

        this.pendingClientDataService.create(pendingClientModel);

        this.emailService.sendActivateEmail(email, token);
    }

    @Transactional
    public void activate(AuthActivateRequestDto requestDto) {
        String token = requestDto.getToken();

        String email = this.tokenService.validateActivateToken(token);

        PendingClientModel pendingPatientModel = this.pendingClientDataService.getByEmail(email);

        ClientModel clientModel = this.clientMapper.toModel(pendingPatientModel);

        this.clientService.create(clientModel);

        this.pendingClientDataService.deleteById(pendingPatientModel.getId());
    }

    @Transactional
    public String login(AuthLoginRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            requestDto.getEmail(),
            requestDto.getPassword()
        );

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return this.tokenService.generateAccessToken(authentication);
    }

    @Transactional
    public void forgotPassword(AuthForgotPasswordRequestDto requestDto) {
        String email = requestDto.getEmail();

        log.info("Attempting to reset password for email: {}", email);

        UserModel userModel = this.userDataService.getByEmail(email);

        UUID userId = userModel.getId();

        String token = this.tokenService.generateResetPasswordToken(email, userId);

        System.out.println(token);

        this.emailService.sendResetPasswordEmail(email, token);

        log.info("Successfully sent reset password email to: {}", email);
    }

    @Transactional
    public void resetPassword(AuthResetPasswordRequestDto requestDto) {
        String token = requestDto.getToken();
        String newPassword = requestDto.getNewPassword();

        String email = this.tokenService.validateResetPasswordToken(token);

        UserModel userModel = this.userDataService.getByEmail(email);

        String hashedPassword = this.passwordEncoder.encode(newPassword);

        userModel.setPassword(hashedPassword);
    }

//
//    public void changeEmail(UserChangeEmail requestDto) {
//        String newEmail = requestDto.getNewEmail();
//
//        UserModel userModel = this.securityService.getModelCurrentUser();
//
//        UUID userId = userModel.getId();
//        String userEmail = userModel.getEmail();
//
//        String token = this.jwtService.generateChangeEmailToken(userId, newEmail);
//
//        System.out.println(token);
//
//        this.emailService.sendChangeEmail(userEmail, token);
//    }
//
//    @Transactional
//    public void confirmChangeEmail(AuthConfirmChangeEmailRequestDto requestDto) {
//        String token = requestDto.getToken();
//
//        String subject = this.jwtService.extractSubject(token);
//
//        UUID userId = UUID.fromString(subject);
//
//        UserModel userModel = this.userService.getModelById(userId);
//
////        userModel.setEmail();
//    }
//
//    public void changePassword(UserChangePassword requestDto) {
//        if (requestDto.getOldPassword().equals(requestDto.getNewPassword())) {
//            throw new RuntimeException("New password must be different from the old password");
//        }
//
//        if(!requestDto.getConfirmPassword().equals(requestDto.getNewPassword())) {
//            throw new RuntimeException("Passwords does not match");
//        }
//
//        UserModel userModel = this.securityService.getModelCurrentUser();
//
//        if (!this.passwordEncoder.matches(requestDto.getOldPassword(), userModel.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        String hashedPassword = this.passwordEncoder.encode(requestDto.getNewPassword());
//
//        userModel.setPassword(hashedPassword);
//
//        userService.save(userModel);
//    }
}