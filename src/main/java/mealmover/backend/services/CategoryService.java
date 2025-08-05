package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.CategoryCreateRequestDto;
import mealmover.backend.dtos.responses.CategoryResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.interfaces.CategoryRatingDto;
import mealmover.backend.mapper.CategoryMapper;
import mealmover.backend.models.CategoryModel;
import mealmover.backend.records.CategoryData;
import mealmover.backend.repositories.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    private final FileStorageService fileStorageService;

    public CategoryResponseDto create(MultipartFile image, CategoryCreateRequestDto requestDto) {

        String name = requestDto.getName();

        log.info("Attempting to create an Category with name: {}", name);

        String imageUri = this.fileStorageService.storeImage(image, "categories", false);

        CategoryModel categoryModel = this.categoryMapper.toModel(requestDto);
        categoryModel.setImageUri(imageUri);

        CategoryModel savedCategoryModel = this.categoryRepository.save(categoryModel);

        log.info("Successfully created Size with name: {}", name);

        return this.categoryMapper.toDto(savedCategoryModel);
    }

    public void seed(CategoryData data) {
        String name = data.name();

        log.info("Attempting to seed an category with name: {}", name);

        if (this.categoryRepository.existsByName(name)) {
            log.info("Category with name {} already exists, skipping seeding.", name);
            return;
        }

        CategoryModel categoryModel = this.categoryMapper.toModel(data);

        this.categoryRepository.save(categoryModel);

        log.info("Successfully seeded category with name: {}", name);
    }

    public List<CategoryResponseDto> getAll() {
        log.info("Getting all categories");
        return this.categoryRepository
            .findAll()
            .stream()
            .map(this.categoryMapper::toDto)
            .toList();
    }

    public CategoryResponseDto getById(UUID id) {
        log.info("Getting category dto by id: {}", id);
        return this.categoryRepository
            .findById(id)
            .map(this.categoryMapper::toDto)
            .orElseThrow(() -> new NotFoundException("Category not found by id: " + id));
    }

    public CategoryModel getModelById(UUID id) {
        log.info("Getting category model by id: {}", id);
        return this.categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Category not found by id: " + id));
    }

    public void deleteById(UUID id) {
        log.info("Getting Size by id: {}", id);

        if (this.categoryRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Category not found by id: " + id);
        }

        this.categoryRepository.deleteById(id);

        log.info("Category with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all Categories..");
        this.categoryRepository.deleteAll();
        log.info("Categories deleted!");
    }

    public CategoryModel getModelByName(String category) {
    log.info("Getting category model by name: {}", category);
        return this.categoryRepository
            .findByName(category)
            .orElseThrow(() -> new NotFoundException("Category not found by name: " + category));
    }

    public List<CategoryResponseDto> getTopRatedCategories(int limit) {
        Pageable pageable = PageRequest.of(0, limit);
        List<CategoryRatingDto> topCategories = this.categoryRepository.findTopRatedCategories(pageable);
        return this.categoryMapper.ratingDtosToResponseDtos(topCategories);
    }
}