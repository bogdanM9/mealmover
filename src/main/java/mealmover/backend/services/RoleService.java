package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.RoleCreateRequestDto;
import mealmover.backend.dtos.requests.RoleUpdateRequestDto;
import mealmover.backend.dtos.responses.RoleResponseDto;
import mealmover.backend.messages.RoleMessages;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.RoleMapper;
import mealmover.backend.models.RoleModel;
import mealmover.backend.repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleMapper mapper;
    private final RoleMessages messages;
    private final RoleRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(RoleService.class);

    public RoleResponseDto create(RoleCreateRequestDto requestDto) {
        String name = requestDto.getName();

        logger.info("Attempting to create a role with name: {}", name);

        if (this.repository.findByName(name).isPresent()) {
            throw new ConflictException(this.messages.alreadyExistsByName());
        }

        RoleModel roleModel = this.mapper.toModel(requestDto);
        RoleModel savedRoleModel = this.repository.save(roleModel);

        logger.info("Successfully created role with name: {}", name);

        return this.mapper.toDto(savedRoleModel);
    }

    public RoleModel getOrCreate(String name) {
        logger.info("Attempting to get or create role model with name: {}", name);
        return this.repository
            .findByName(name)
            .orElseGet(() -> this.repository.save(new RoleModel(name)));
    }

    public List<RoleResponseDto> getAll() {
        logger.info("Getting all roles");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public RoleResponseDto getById(UUID id) {
        logger.info("Getting role by id: {}", id);
        return this.repository
                .findById(id)
                .map(this.mapper::toDto)
                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }

    public Optional<RoleModel> getModelByName(String name){
        return this.repository.findByName(name);
    }

    public RoleResponseDto updateById(UUID id, RoleUpdateRequestDto requestDto) {
        logger.info("Attempting to update role with id: {}", id);

        RoleModel role = this.repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));

        role.setName(requestDto.getName());

        RoleModel updatedRole = this.repository.save(role);

        logger.info("Successfully update role with id: {}", id);

        return this.mapper.toDto(updatedRole);
    }

    public void deleteById(UUID id) {
        logger.info("Getting role by id: {}", id);
        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }
        this.repository.deleteById(id);
        logger.info("Role with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all roles..");
        this.repository.deleteAll();
        logger.info("Roles deleted!");
    }
}