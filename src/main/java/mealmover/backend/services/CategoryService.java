package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.CreateCategoryRequestDto;
import mealmover.backend.dtos.responses.CategoryResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.CategoryMapper;
import mealmover.backend.messages.CategoryMessages;
import mealmover.backend.models.CategoryModel;
import mealmover.backend.repositories.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryMapper mapper;
    private final CategoryMessages messages;
    private final CategoryRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);


    public CategoryResponseDto create(CreateCategoryRequestDto requestDto) {
        String name = requestDto.getName();

        logger.info("Attempting to create an Category with name: {}", name);

        CategoryModel categoryModel = this.mapper.toModel(requestDto);

        CategoryModel savedCategoryModel = this.repository.save(categoryModel);

        logger.info("Successfully created Size with name: {}", name);

        return this.mapper.toDto(savedCategoryModel);
    }

    public boolean existsById(UUID id) {
        return this.repository.existsById(id);
    }

    public List<CategoryResponseDto> getAll() {
        logger.info("Getting all Categories");
        return this.repository
                .findAll()
                .stream()
                .map(this.mapper::toDto)
                .toList();
    }

    public CategoryResponseDto getById(UUID id) {
        logger.info("Getting category dto by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }

    public CategoryModel getModelById(UUID id) {
        logger.info("Getting category model by id: {}", id);
        return this.repository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }

    public void deleteById(UUID id) {
        logger.info("Getting Size by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("Category with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all Categories..");
        this.repository.deleteAll();
        logger.info("Categories deleted!");
    }

}
