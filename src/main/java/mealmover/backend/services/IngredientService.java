package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.AllergenCreateDto;
import mealmover.backend.dtos.IngredientCreateDto;
import mealmover.backend.dtos.requests.IngredientCreateRequestDto;
import mealmover.backend.dtos.requests.IngredientUpdateRequestDto;
import mealmover.backend.dtos.responses.IngredientResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.IngredientMapper;
import mealmover.backend.messages.IngredientMessages;
import mealmover.backend.models.AllergenModel;
import mealmover.backend.models.IngredientModel;
import mealmover.backend.repositories.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final AllergenService allergenService;
    private final IngredientMapper mapper;
    private final IngredientMessages messages;
    private final IngredientRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(IngredientService.class);

    public IngredientResponseDto create(IngredientCreateRequestDto requestDto) {
        String name = requestDto.getName();

        logger.info("Attempting to create an ingredient with name: {}", name);

        if (this.repository.findByName(name).isPresent()) {
            throw new ConflictException(this.messages.alreadyExistsByName());
        }

        IngredientModel ingredientModel = this.mapper.toModel(requestDto);

        IngredientModel savedIngredientModel = this.repository.save(ingredientModel);

        logger.info("Successfully created ingredient with name: {}", name);

        return this.mapper.toDto(savedIngredientModel);
    }

    public List<IngredientResponseDto> getAll() {
        logger.info("Getting all ingredients");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public IngredientResponseDto getById(UUID id) {
        logger.info("Getting ingredient by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }

    public IngredientResponseDto updateById(UUID id, IngredientUpdateRequestDto requestDto) {
        logger.info("Attempting to update ingredient with id: {}", id);

        IngredientModel ingredient = this.repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));

        ingredient.setName(requestDto.getName());

        IngredientModel updatedIngredient = this.repository.save(ingredient);

        logger.info("Successfully update ingredient with id: {}", id);

        return this.mapper.toDto(updatedIngredient);
    }

    public void deleteById(UUID id) {
        logger.info("Getting ingredient by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("Ingredient with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all ingredients..");
        this.repository.deleteAll();
        logger.info("Ingredients deleted!");
    }

    public IngredientModel getOrCreate(IngredientModel ingredientModel) {
        String name = ingredientModel.getName();

        logger.info("Attempting to get or create ingredient with name: {}", name);

        if(this.repository.findByName(name).isPresent()) {
            return this.repository.findByName(name).get();
        }

        Set<AllergenModel> savedAllergens = ingredientModel.getAllergens()
            .stream()
            .map(allergenService::getOrCreate)
            .collect(Collectors.toSet());

        ingredientModel.setAllergens(savedAllergens);

        return this.repository.save(ingredientModel);
    }

    public IngredientModel getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new NotFoundException("Ingredient with name " + name + " not found"));
    }
}
