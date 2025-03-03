package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.DriverCreateRequestDto;
import mealmover.backend.dtos.responses.DriverResponseDto;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.DriverMapper;
import mealmover.backend.messages.DriverMessages;
import mealmover.backend.models.DriverModel;
import mealmover.backend.models.RoleModel;
import mealmover.backend.repositories.DriverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverService {

    private final DriverMapper mapper;
    private final DriverMessages messages;
    private final RoleService roleService;
    private final UserService userService;
    private final DriverRepository repository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(DriverService.class);

    public DriverResponseDto create(DriverCreateRequestDto requestDto) {
        String email = requestDto.getEmail();

        logger.info("Attempting to create a Driver with email: {}", email);

        if (this.userService.existsByEmail(email)) {
            throw new ConflictException(this.messages.alreadyExistsByEmail());
        }

        RoleModel role = this.roleService.getOrCreate(Role.DRIVER.toCapitalize());

        DriverModel driver = this.mapper.toModel(requestDto);

        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());

        driver.getRoles().add(role);
        driver.setPassword(hashedPassword);

        DriverModel savedDriver = this.repository.save(driver);

        logger.info("Successfully created Driver with name: {}", email);

        return this.mapper.toDto(savedDriver);
    }

    public List<DriverResponseDto> getAll() {
        logger.info("Getting all drivers");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public DriverResponseDto getById(UUID id) {
        logger.info("Getting driver by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }


    public void deleteById(UUID id) {
        logger.info("Getting drivers by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("Driver with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all drivers..");
        this.repository.deleteAll();
        logger.info("Drivers deleted!");
    }
}
