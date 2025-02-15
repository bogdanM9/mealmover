package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.ExtraIngredientCreateDto;
import mealmover.backend.dtos.SizeCreateDto;
import mealmover.backend.dtos.requests.CreateSizeRequestDto;
import mealmover.backend.dtos.requests.ExtraIngredientCreateRequestDto;
import mealmover.backend.dtos.requests.IngredientCreateRequestDto;
import mealmover.backend.dtos.responses.SizeResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.SizeMapper;
import mealmover.backend.messages.SizeMessages;
import mealmover.backend.models.AllergenModel;
import mealmover.backend.models.IngredientModel;
import mealmover.backend.models.SizeModel;
import mealmover.backend.repositories.SizeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SizeService {
    private final SizeMapper mapper;
    private final SizeMessages messages;
    private final SizeRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(SizeService.class);

    public SizeResponseDto create(CreateSizeRequestDto requestDto) {
        String name = requestDto.getName();

        logger.info("Attempting to create an Size with name: {}", name);

        SizeModel sizeModel = this.mapper.toModel(requestDto);

        SizeModel savedSizeModel = this.repository.save(sizeModel);

        logger.info("Successfully created Size with name: {}", name);

        return this.mapper.toDto(savedSizeModel);
    }

    public List<SizeResponseDto> getAll() {
        logger.info("Getting all Sizes");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public SizeResponseDto getById(UUID id) {
        logger.info("Getting size by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }


    public void deleteById(UUID id) {
        logger.info("Getting Size by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("Size with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all Sizes..");
        this.repository.deleteAll();
        logger.info("Sizes deleted!");
    }

    public SizeModel getModelById(UUID sizeId) {
        return this.repository
            .findById(sizeId)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }

    public SizeModel getOrCreate(SizeCreateDto sizeCreateDto) {
        String name = sizeCreateDto.getName();

        logger.info("Attempting to get or create size with name: {}", name);

        SizeModel sizeModel = this.mapper.toModel(sizeCreateDto);

        return this.repository
            .findByName(name)
            .orElseGet(() -> this.repository.save(sizeModel));
    }
}
