package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.exceptions.UnauthorizedException;
import mealmover.backend.mapper.UserMapper;
import mealmover.backend.messages.RoleMessages;
import mealmover.backend.messages.UserMessages;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.UserModel;
import mealmover.backend.repositories.UserRepository;
import mealmover.backend.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final UserMessages messages;
    private final SecurityUtils securityUtils;
    private final UserRepository repository;

    @Transactional(readOnly = true)
    public UserResponseDto getCurrentUser() {
        String email = this.securityUtils.getCurrentUser().getUsername();

        UserModel userModel = this.repository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException("There is no user with this email"));

        String model = userModel.getClass().getSimpleName();

        return switch (model) {
//            case "AdminModel" -> this.adminMapper.toDto((AdminModel) userModel);
//            case "DoctorModel" -> this.doctorMapper.toDto((DoctorModel) userModel);
//            case "PatientModel" -> this.patientMapper.toDto((PatientModel) userModel);
            default -> this.mapper.toDto(userModel);
        };
    }


//    public UserResponseDto create(UserCreateRequestDto requestDto) {
//        String name = requestDto.getFirstName();
//
//        log.info("Attempting to create a User with name: {}", name);
//
//        if (this.repository.findByFirstName(name).isPresent()) {
//            throw new ConflictException(this.roleMessages.create());
//        }
//
//        UserModel userModel = this.mapper.toModel(requestDto);
//        String hashedPassword = this.passwordEncoder.encode(userModel.getPassword());
//        userModel.setPassword(hashedPassword);
//
//        UserModel savedUserModel = this.repository.save(userModel);
//
//        log.info("Successfully created User with name: {}", name);
//
//        return this.mapper.toDto(savedUserModel);
//    }

    public void save(UserModel userModel) {
        this.repository.save(userModel);
    }

    public List<UserResponseDto> getAll() {
        log.info("Getting all users");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }


    @Transactional(readOnly = true)
    public UserModel getModelByEmail(String email) {
        return this.repository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(
                messages.notFoundByEmail()
            ));
    }

    public boolean existsByEmail(String email){
        return this.repository.existsByEmail(email);
    }

    public UserResponseDto getById(UUID id) {
        log.info("Getting User by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(
                    messages.notFoundById()
            ));
    }

    public void deleteById(UUID id) {
        log.info("Getting user by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }

        this.repository.deleteById(id);

        log.info("User with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all users..");
        this.repository.deleteAll();
        log.info("Users deleted!");
    }

}
