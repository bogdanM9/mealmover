package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.exceptions.UnauthorizedException;
import mealmover.backend.mapper.UserMapper;
import mealmover.backend.messages.RoleMessages;
import mealmover.backend.messages.UserMessages;
import mealmover.backend.models.UserModel;
import mealmover.backend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMessages userMessages;
    private final UserMapper mapper;

    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);


//    public UserResponseDto create(UserCreateRequestDto requestDto) {
//        String name = requestDto.getFirstName();
//
//        logger.info("Attempting to create a User with name: {}", name);
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
//        logger.info("Successfully created User with name: {}", name);
//
//        return this.mapper.toDto(savedUserModel);
//    }

    public void save(UserModel userModel) {
        this.repository.save(userModel);
    }

    public List<UserResponseDto> getAll() {
        logger.info("Getting all users");
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
            .orElseThrow(() -> new UnauthorizedException(
                    userMessages.invalidCredentials()
            ));
    }

    public boolean existsByEmail(String email){
        return this.repository
            .findByEmail(email)
            .isPresent();
    }

    public UserResponseDto getById(UUID id) {
        logger.info("Getting User by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(
                    userMessages.notFoundById()
            ));
    }

    public void deleteById(UUID id) {
        logger.info("Getting user by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(userMessages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("User with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all users..");
        this.repository.deleteAll();
        logger.info("Users deleted!");
    }

}
