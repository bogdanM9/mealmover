package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.AllergenCreateDto;
import mealmover.backend.dtos.responses.AllergenResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.AllergenMapper;
import mealmover.backend.models.AllergenModel;
import mealmover.backend.repositories.AllergenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Slf4j
@Service
@RequiredArgsConstructor
public class AllergenService {
    private final AllergenMapper mapper;
    private final AllergenRepository repository;

    public AllergenResponseDto create(AllergenCreateDto dto) {
        String name = dto.getName();

        log.info("Attempting to create an Allergen with name: {}", name);

        AllergenModel allergen = this.mapper.toModel(dto);

        AllergenModel savedAllergen = this.repository.save(allergen);

        log.info("Successfully created allergen with name: {}", name);

        return this.mapper.toDto(savedAllergen);
    }

    public AllergenModel getOrCreate(AllergenModel allergenModel) {
        String name = allergenModel.getName();

        log.info("Attempting to get or create allergen with name: {}", name);

        return this.repository
            .findByName(name)
            .orElseGet(() -> this.repository.save(allergenModel));
    }

    public List<AllergenResponseDto> getAll() {
        log.info("Getting all Allergens");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public AllergenResponseDto getById(UUID id) {
        log.info("Getting allergen by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException("Allergen not found with id: " + id));
    }


    public void deleteById(UUID id) {
        log.info("Getting Allergen by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException("Allergen not found with id: " + id);
        }

        this.repository.deleteById(id);

        log.info("Allergen with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all Allergens..");
        this.repository.deleteAll();
        log.info("Allergens deleted!");
    }

    public boolean existsByName(String name) {
        return this.repository.existsByName(name);
    }
}
