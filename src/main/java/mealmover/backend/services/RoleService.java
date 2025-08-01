package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.RoleConstants;
import mealmover.backend.dtos.requests.RoleCreateRequestDto;
import mealmover.backend.dtos.requests.RoleUpdateRequestDto;
import mealmover.backend.dtos.responses.RoleResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.RoleMapper;
import mealmover.backend.models.RoleModel;
import mealmover.backend.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;

    @Transactional
    public RoleResponseDto create(RoleCreateRequestDto requestDto) {
        String name = requestDto.getName();

        log.info("Attempting to create a role with name: {}", name);

        if (this.roleRepository.findByName(name).isPresent()) {
            throw new ConflictException(RoleConstants.ALREADY_EXISTS_BY_NAME);
        }

        RoleModel roleModel = this.roleMapper.toModel(requestDto);
        RoleModel savedRoleModel = this.roleRepository.save(roleModel);

        log.info("Successfully created role with name: {}", name);

        return this.roleMapper.toDto(savedRoleModel);
    }

    @Transactional(readOnly = true)
    public boolean existsByName(String name) {
        log.info("Checking if role with name {} exists", name);
        return this.roleRepository.existsByName(name);
    }

    @Transactional(readOnly = true)
    public RoleModel getModelByName(String name) {
        log.info("Getting role with name {} exists", name);
        return this.roleRepository
            .findByName(name)
            .orElseThrow(() -> new NotFoundException(RoleConstants.NOT_FOUND_BY_NAME));
    }

    public RoleModel getOrCreate(String name) {
        log.info("Attempting to get or create role model with name: {}", name);
        return this.roleRepository
            .findByName(name)
            .orElseGet(() -> this.roleRepository.save(new RoleModel(name)));
    }

    public List<RoleResponseDto> getAll() {
        log.info("Getting all roles");
        return this.roleRepository
            .findAll()
            .stream()
            .map(this.roleMapper::toDto)
            .toList();
    }

    public RoleResponseDto getById(UUID id) {
        log.info("Getting role by id: {}", id);
        return this.roleRepository
                .findById(id)
                .map(this.roleMapper::toDto)
                .orElseThrow(() -> new NotFoundException(RoleConstants.NOT_FOUND_BY_ID));
    }


    public RoleResponseDto updateById(UUID id, RoleUpdateRequestDto requestDto) {
        log.info("Attempting to update role with id: {}", id);

        RoleModel role = this.roleRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(RoleConstants.NOT_FOUND_BY_ID));

        role.setName(requestDto.getName());

        RoleModel updatedRole = this.roleRepository.save(role);

        log.info("Successfully update role with id: {}", id);

        return this.roleMapper.toDto(updatedRole);
    }

    public void deleteById(UUID id) {
        log.info("Getting role by id: {}", id);
        if (this.roleRepository.findById(id).isEmpty()) {
            throw new NotFoundException(RoleConstants.NOT_FOUND_BY_ID);
        }
        this.roleRepository.deleteById(id);
        log.info("Role with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all roles..");
        this.roleRepository.deleteAll();
        log.info("Roles deleted!");
    }
}