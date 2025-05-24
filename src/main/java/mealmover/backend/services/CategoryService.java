package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.CreateCategoryRequestDto;
import mealmover.backend.dtos.responses.CategoryResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.CategoryMapper;
import mealmover.backend.models.CategoryModel;
import mealmover.backend.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository repository;


    public CategoryResponseDto create(CreateCategoryRequestDto requestDto) {
        String name = requestDto.getName();

        log.info("Attempting to create an Category with name: {}", name);

        CategoryModel categoryModel = this.mapper.toModel(requestDto);

        CategoryModel savedCategoryModel = this.repository.save(categoryModel);

        log.info("Successfully created Size with name: {}", name);

        return this.mapper.toDto(savedCategoryModel);
    }

    public boolean existsById(UUID id) {
        return this.repository.existsById(id);
    }

    public List<CategoryResponseDto> getAll() {
        log.info("Getting all Categories");
        return this.repository
                .findAll()
                .stream()
                .map(this.mapper::toDto)
                .toList();
    }

    public CategoryResponseDto getById(UUID id) {
        log.info("Getting category dto by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException("Category not found by id: " + id));
    }

    public CategoryModel getModelById(UUID id) {
        log.info("Getting category model by id: {}", id);
        return this.repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Category not found by id: " + id));
    }

    public void deleteById(UUID id) {
        log.info("Getting Size by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException("Category not found by id: " + id);
        }

        this.repository.deleteById(id);

        log.info("Category with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all Categories..");
        this.repository.deleteAll();
        log.info("Categories deleted!");
    }

}
