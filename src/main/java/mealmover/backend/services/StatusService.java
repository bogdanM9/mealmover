package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.StatusCreateRequestDto;
import mealmover.backend.dtos.responses.StatusResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.StatusMapper;
import mealmover.backend.models.StatusModel;
import mealmover.backend.repositories.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;
    private final StatusMapper statusMapper;
    private final Logger logger = LoggerFactory.getLogger(StatusService.class);

    public StatusResponseDto create(StatusCreateRequestDto dto) {
        String name = dto.getName();

        logger.info("Attempting to create a Status with name: {}", name);

        StatusModel status = this.statusMapper.toModel(dto);

        StatusModel savedStatus = this.statusRepository.save(status);

        logger.info("Successfully created status with name: {}", name);

        return this.statusMapper.toDto(savedStatus);
    }

    public List<StatusResponseDto> getAll() {
        logger.info("Getting all Statuses");
        return this.statusRepository
            .findAll()
            .stream()
            .map(this.statusMapper::toDto)
            .toList();
    }

    public StatusResponseDto getById(UUID id) {
        logger.info("Getting status by id: {}", id);

        StatusModel status = this.statusRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Status not found"));

        return this.statusMapper.toDto(status);
    }

    public StatusModel getModelById(UUID id) {
        logger.info("Getting status by id: {}", id);

        return this.statusRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Status not found"));
    }

    public StatusModel getOrCreate(String name) {
        logger.info("Attempting to get or create status model with name: {}", name);
        return this.statusRepository
            .findByName(name)
            .orElseGet(() -> this.statusRepository.save(new StatusModel(name)));
    }

    public void deleteById(UUID id) {
        logger.info("Getting Status by id: {}", id);

        if (this.statusRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Status not found");
        }
    }

    public void deleteAll() {
        logger.info("Deleting all Allergens..");
        this.statusRepository.deleteAll();
        logger.info("Allergens deleted!");
    }
}