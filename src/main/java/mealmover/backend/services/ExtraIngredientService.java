package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ExtraIngredientCreateRequestDto;
import mealmover.backend.dtos.responses.ExtraIngredientResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.ExtraIngredientMapper;
import mealmover.backend.messages.ExtraIngredientMessages;
import mealmover.backend.models.ExtraIngredientModel;
import mealmover.backend.repositories.ExtraIngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExtraIngredientService {

    private final ExtraIngredientMapper mapper;
    private final ExtraIngredientMessages messages;
    private final ExtraIngredientRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ExtraIngredientService.class);

    public ExtraIngredientResponseDto create(ExtraIngredientCreateRequestDto requestDto) {
        String name = requestDto.getName();

        logger.info("Attempting to create an Extra ingredient with name: {}", name);

        if (this.repository.findByName(name).isPresent()) {
            throw new ConflictException(this.messages.alreadyExistsByName());
        }

        ExtraIngredientModel extraIngredientModel = this.mapper.toModel(requestDto);

        ExtraIngredientModel savedExtraIngredientModel = this.repository.save(extraIngredientModel);

        logger.info("Successfully created Extra ingredient with name: {}", name);

        return this.mapper.toDto(savedExtraIngredientModel);
    }

    public List<ExtraIngredientResponseDto> getAll() {
        logger.info("Getting all Extra ingredients");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public ExtraIngredientResponseDto getById(UUID id) {
        logger.info("Getting Extra ingredient by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }


    public void deleteById(UUID id) {
        logger.info("Getting Extra ingredient by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("Extra Ingredient with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all Extra ingredients..");
        this.repository.deleteAll();
        logger.info("Extra Ingredients deleted!");
    }

    public boolean existsByName(String name) {
        return this.repository.existsByName(name);
    }

    public ExtraIngredientModel getOrCreate(ExtraIngredientModel extraIngredientModel) {
        String name = extraIngredientModel.getName();

        logger.info("Attempting to get or create extra ingredient with name: {}", name);

        return this.repository
            .findByName(name)
            .orElseGet(() -> this.repository.save(extraIngredientModel));
    }

    public ExtraIngredientModel getModelById(UUID extraIngredientId) {
        return this.repository.findById(extraIngredientId)
            .orElseThrow(() -> {
                logger.error("Extra ingredient with id {} not found", extraIngredientId);
                return new NotFoundException("Extra ingredient not found");
            });
    }
}
