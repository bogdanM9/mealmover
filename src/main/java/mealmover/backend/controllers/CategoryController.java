//package mealmover.backend.controllers;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import mealmover.backend.dtos.requests.CreateCategoryRequestDto;
//import mealmover.backend.dtos.responses.CategoryResponseDto;
//import mealmover.backend.messages.CategoryMessages;
//import mealmover.backend.services.CategoryService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/categories")
//@RequiredArgsConstructor
//public class CategoryController {
//    private final CategoryService service;
//    private final CategoryMessages messages;
//
//    @PostMapping
//    public ResponseEntity<CategoryResponseDto> create(@Valid @RequestBody CreateCategoryRequestDto requestDto) {
//        CategoryResponseDto response = this.service.create(requestDto);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CategoryResponseDto>> getAll() {
//        return ResponseEntity.ok(this.service.getAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CategoryResponseDto> getById(@PathVariable UUID id) {
//        return ResponseEntity.ok(this.service.getById(id));
//    }
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
//        this.service.deleteById(id);
//        return ResponseEntity.ok(messages.deleted());
//    }
//
//    @DeleteMapping
//    public ResponseEntity<String> deleteAll() {
//        this.service.deleteAll();
//        return ResponseEntity.ok(messages.deletedAll());
//    }
//}
