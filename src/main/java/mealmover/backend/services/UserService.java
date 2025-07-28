package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.UserConstants;
import mealmover.backend.dtos.requests.UserCreateRequestDto;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.exceptions.UnauthorizedException;
import mealmover.backend.mapper.UserMapper;
import mealmover.backend.models.UserModel;
import mealmover.backend.repositories.UserRepository;
import mealmover.backend.security.SecurityUtils;
import mealmover.backend.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


//    @Transactional(readOnly = true)
//    public UserResponseDto getCurrentUser() {
//        String email = this.securityUtils.getCurrentUser().getUsername();
//
//        UserModel userModel = this.repository
//            .findByEmail(email)
//            .orElseThrow(() -> new NotFoundException("There is no user with this email"));
//
//        String model = userModel.getClass().getSimpleName();
//
//        return switch (model) {
////            case "AdminModel" -> this.adminMapper.toDto((AdminModel) userModel);
////            case "DoctorModel" -> this.doctorMapper.toDto((DoctorModel) userModel);
////            case "PatientModel" -> this.patientMapper.toDto((PatientModel) userModel);
//            default -> this.mapper.toDto(userModel);
//        };
//    }


//    public UserResponseDto create(UserCreateRequestDto requestDto) {
//        log.info("Creating user with email {}", requestDto.getEmail());
//        if (this.userRepository.existsByEmail(requestDto.getEmail())) {
//            log.error("User with email {} already exists", requestDto.getEmail());
//            throw new UnauthorizedException("User with this email already exists");
//        }
//
//        UserModel userModel = this.userMapper.toModel(requestDto);
//        userModel.setRole(Role.USER);
//        String hashedPassword = this.passwordEncoder.encode(userModel.getPassword());
//        userModel.setPassword(hashedPassword);
//        this.userRepository.save(userModel);
//
//        return this.userMapper.toDto(userModel);
//    }

    @Transactional
    public void create(UserModel userModel) {
        this.userRepository.save(userModel);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> getAll() {
        log.info("Getting all users");
        return this.userRepository
            .findAll()
            .stream()
            .map(this.userMapper::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public UserModel getModelById(UUID id) {
        return this.userRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(UserConstants.NOT_FOUND_BY_ID));
    }

    @Transactional(readOnly = true)
    public UserModel getModelByEmail(String email) {
        return this.userRepository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(UserConstants.NOT_FOUND_BY_EMAIL));
    }

    public boolean existsByEmail(String email){
        log.info("Checking if user with email {} exists", email);
        return this.userRepository.existsByEmail(email);
    }

    public UserResponseDto getById(UUID id) {
        log.info("Getting User by id: {}", id);
        return this.userRepository
            .findById(id)
            .map(this.userMapper::toDto)
            .orElseThrow(() -> new NotFoundException(UserConstants.NOT_FOUND_BY_ID));
    }

    public void deleteById(UUID id) {
        log.info("Getting user by id: {}", id);

        if (this.userRepository.findById(id).isEmpty()) {
            throw new NotFoundException(UserConstants.NOT_FOUND_BY_ID);
        }

        this.userRepository.deleteById(id);

        log.info("User with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all users..");
        this.userRepository.deleteAll();
        log.info("Users deleted!");
    }

    public void save(UserModel userModel) {
        log.info("Saving user with email: {}", userModel.getEmail());
        this.userRepository.save(userModel);
        log.info("User with email {} saved successfully", userModel.getEmail());
    }
}
