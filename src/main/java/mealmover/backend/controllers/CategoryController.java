package mealmover.backend.controllers;


import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.CategoryCreateRequestDto;
import mealmover.backend.dtos.responses.CategoryResponseDto;
import mealmover.backend.dtos.responses.MessageResponseDto;
import mealmover.backend.services.CategoryService;
import mealmover.backend.services.utils.MapperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final MapperService mapperService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(
        @RequestParam("image") MultipartFile image,
        @RequestParam("data") String data
    ) {
        CategoryCreateRequestDto requestDto = this.mapperService.parseCategoryCreateData(data);
        CategoryResponseDto response = this.categoryService.create(image, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        List<CategoryResponseDto> responseDtos = this.categoryService.getAll();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable UUID id) {
        CategoryResponseDto responseDto = this.categoryService.getById(id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteById(@PathVariable UUID id) {
        this.categoryService.deleteById(id);
        MessageResponseDto responseDto = MessageResponseDto.success("Category deleted successfully.");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponseDto> deleteAll() {
        this.categoryService.deleteAll();
        MessageResponseDto responseDto = MessageResponseDto.success("All categories deleted successfully.");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/top-rated-categories")
    public ResponseEntity<List<CategoryResponseDto>> getTopRatedCategories(
        @RequestParam(name = "limit", required = false, defaultValue = "4") int limit
    ) {
        List<CategoryResponseDto> responseDtos = this.categoryService.getTopRatedCategories(limit);
        return ResponseEntity.ok(responseDtos);
    }
}
