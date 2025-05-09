package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.AuthActivateClientRequestDto;
import mealmover.backend.dtos.AuthRegisterClientRequestDto;
import mealmover.backend.dtos.requests.AuthLoginRequestDto;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.mapper.ClientMapper;
import mealmover.backend.mapper.PendingClientMapper;
import mealmover.backend.messages.PendingClientMessages;
import mealmover.backend.messages.UserMessages;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.PendingClientModel;
import mealmover.backend.models.RoleModel;
import mealmover.backend.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final RoleService roleService;
    private final EmailService emailService;
    private final UserMessages userMessages;
    private final TokenService tokenService;
    private final ClientMapper clientMapper;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final PendingClientMapper pendingClientMapper;
    private final PendingClientService pendingClientService;
    private final PendingClientMessages pendingClientMessages;
    private final AuthenticationManager authenticationManager;

    public void registerClient(AuthRegisterClientRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = requestDto.getPassword();

        log.info("Attempting to create an Pending client with email: {}", email);

        if(this.userService.existsByEmail(email)) {
            throw new ConflictException(this.userMessages.alreadyExistsByEmail(email));
        }

        if (this.pendingClientService.existsByEmail(email)) {
            throw new ConflictException(this.pendingClientMessages.alreadyExistsByEmail());
        }

        PendingClientModel pendingClientModel = this.pendingClientMapper.toModel(requestDto);

        String hashedPassword = this.passwordEncoder.encode(password);

        pendingClientModel.setPassword(hashedPassword);

        String token = this.tokenService.generateRegistrationClientToken(email);

        pendingClientModel.setToken(token);

        this.pendingClientService.create(pendingClientModel);

        this.emailService.sendActivateClientAccountEmail(email, token);
    }

    public void activateClient(AuthActivateClientRequestDto requestDto) {
        String token = requestDto.getToken();

        String email = this.tokenService.validateRegistrationClientToken(token);

        PendingClientModel pendingPatientModel = this.pendingClientService.getModelByEmail(email);

        ClientModel clientModel = this.clientMapper.toModel(pendingPatientModel);

        RoleModel roleModel = this.roleService.getOrCreate(Role.CLIENT.toConvert());

        clientModel.addRole(roleModel);

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
//
//    public UserResponseDto getAuthUser() {
//        String email = this.securityService.getLoggedInUserEmail();
//        UserModel userModel = userService.getModelByEmail(email);
//        return userMapper.toDto(userModel);
//    }
//
//    public void forgotPassword(AuthForgotPasswordRequestDto requestDto) {
//        String email = requestDto.getEmail();
//
//        UserModel userModel = userService.getModelByEmail(email);
//
//        UserDetails userDetails = userMapper.toUserDetails(userModel);
//
//        String token = jwtService.generateResetPasswordToken(userDetails);
//
//        this.emailService.sendForgotPasswordEmail(email, token);
//    }
//
//    public void resetPassword(String token, String newPassword) {
//        String email = jwtService.extractEmail(token);
//
//        UserModel userModel = userService.getModelByEmail(email);
//
//        String hashedPassword = this.passwordEncoder.encode(newPassword);
//
//        userModel.setPassword(hashedPassword);
//
//        userService.save(userModel);
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
//        String email = this.securityService.getLoggedInUserEmail();
//
//        UserModel userModel = userService.getModelByEmail(email);
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
//
//    public void changeEmail(UserChangeEmail requestDto) {
//
//        String email = this.securityService.getLoggedInUserEmail();
//        UserModel userModel = userService.getModelByEmail(email);
//        userModel.setEmail(requestDto.getNewEmail()); // necesar pt token?
//
//        if (!this.passwordEncoder.matches(requestDto.getPassword(), userModel.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        UserDetails userDetails = userMapper.toUserDetails(userModel);
//
//        String emailToken = jwtService.generateChangeEmailToken(userDetails);
//
//        this.emailService.sendChangeEmailEmail(requestDto.getNewEmail(), email, emailToken);
//
//    }
//
//    public void confirmChangeEmail(AuthActivateClientRequestDto requestDto) {
//        String emailFromToken = jwtService.extractEmail(requestDto.getToken());
//        System.out.println(emailFromToken);
//        String loggedInUserEmail = this.securityService.getLoggedInUserEmail();
//        UserModel userModel = userService.getModelByEmail(loggedInUserEmail); // SAU din reuqestDto?
//        System.out.println(userModel.getFirstName());
//        userModel.setEmail(emailFromToken);
//        userService.save(userModel);
//    }
//
//
//
}