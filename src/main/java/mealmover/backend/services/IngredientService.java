package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.IngredientCreateRequestDto;
import mealmover.backend.dtos.requests.IngredientUpdateRequestDto;
import mealmover.backend.dtos.responses.IngredientResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.IngredientMapper;
import mealmover.backend.models.AllergenModel;
import mealmover.backend.models.IngredientModel;
import mealmover.backend.repositories.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class IngredientService {
    private final AllergenService allergenService;
    private final IngredientMapper mapper;
    private final IngredientRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(IngredientService.class);

    public IngredientResponseDto create(IngredientCreateRequestDto requestDto) {
        String name = requestDto.getName();

        log.info("Attempting to create an ingredient with name: {}", name);

        if (this.repository.findByName(name).isPresent()) {
            throw new ConflictException("Ingredient with name " + name + " already exists");
        }

        IngredientModel ingredientModel = this.mapper.toModel(requestDto);

        IngredientModel savedIngredientModel = this.repository.save(ingredientModel);

        log.info("Successfully created ingredient with name: {}", name);

        return this.mapper.toDto(savedIngredientModel);
    }

    public List<IngredientResponseDto> getAll() {
        log.info("Getting all ingredients");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public IngredientResponseDto getById(UUID id) {
        log.info("Getting ingredient by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException("Ingredient not found by id: " + id));
    }

    public IngredientResponseDto updateById(UUID id, IngredientUpdateRequestDto requestDto) {
        log.info("Attempting to update ingredient with id: {}", id);

        IngredientModel ingredient = this.repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Ingredient not found by id: " + id));

        ingredient.setName(requestDto.getName());

        IngredientModel updatedIngredient = this.repository.save(ingredient);

        log.info("Successfully update ingredient with id: {}", id);

        return this.mapper.toDto(updatedIngredient);
    }

    public void deleteById(UUID id) {
        log.info("Getting ingredient by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException("Ingredient not found by id: " + id);
        }

        this.repository.deleteById(id);

        log.info("Ingredient with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all ingredients..");
        this.repository.deleteAll();
        log.info("Ingredients deleted!");
    }

    public IngredientModel getOrCreate(IngredientModel ingredientModel) {
        String name = ingredientModel.getName();

        log.info("Attempting to get or create ingredient with name: {}", name);

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
