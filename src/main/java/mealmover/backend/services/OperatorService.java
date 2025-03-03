package mealmover.backend.services;

import lombok.RequiredArgsConstructor;

import mealmover.backend.dtos.requests.OperatorCreateRequestDto;
import mealmover.backend.dtos.requests.OperatorUpdateRequestDto;
import mealmover.backend.dtos.responses.OperatorResponseDto;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.OperatorMapper;
import mealmover.backend.messages.OperatorMessages;
import mealmover.backend.models.OperatorModel;
import mealmover.backend.models.RoleModel;
import mealmover.backend.repositories.OperatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final OperatorMapper mapper;
    private final OperatorMessages messages;
    private final RoleService roleService;
    private final UserService userService;
    private final OperatorRepository repository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(OperatorService.class);


    public OperatorResponseDto create(OperatorCreateRequestDto requestDto) {
        String email = requestDto.getEmail();

        logger.info("Attempting to create a operators with email: {}", email);

        if (this.userService.existsByEmail(email)) {
            throw new ConflictException(this.messages.alreadyExistsByEmail());
        }

        RoleModel role = this.roleService.getOrCreate(Role.OPERATOR.toCapitalize());

        OperatorModel operator = this.mapper.toModel(requestDto);

        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());

        operator.getRoles().add(role);
        operator.setPassword(hashedPassword);

        OperatorModel savedOperator = this.repository.save(operator);

        logger.info("Successfully created operator with name: {}", email);

        return this.mapper.toDto(savedOperator);
    }

    public List<OperatorResponseDto> getAll() {
        logger.info("Getting all operators");
        return this.repository
                .findAll()
                .stream()
                .map(this.mapper::toDto)
                .toList();
    }

    public OperatorResponseDto getById(UUID id) {
        logger.info("Getting operator by id: {}", id);
        return this.repository
                .findById(id)
                .map(this.mapper::toDto)
                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }

    public OperatorResponseDto updateById(UUID id, OperatorUpdateRequestDto requestDto) {
        logger.info("Attempting to update operator with id: {}", id);

        OperatorModel operator = this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));

        operator.setLastName(requestDto.getLastName());
        operator.setFirstName(requestDto.getFirstName());
        operator.setPhoneNumber(requestDto.getPhoneNumber());

        OperatorModel updatedOperator = this.repository.save(operator);

        logger.info("Successfully update operator with id: {}", id);

        return this.mapper.toDto(updatedOperator);
    }

    public void deleteById(UUID id) {
        logger.info("Getting operators by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("Operator with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all operators..");
        this.repository.deleteAll();
        logger.info("Operators deleted!");
    }
}
