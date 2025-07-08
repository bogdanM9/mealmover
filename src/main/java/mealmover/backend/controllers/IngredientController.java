package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.IngredientCreateRequestDto;
import mealmover.backend.dtos.requests.IngredientUpdateRequestDto;
import mealmover.backend.dtos.responses.IngredientResponseDto;
import mealmover.backend.services.IngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService service;

    @PostMapping
    public ResponseEntity<IngredientResponseDto> create(@Valid @RequestBody IngredientCreateRequestDto requestDto) {
        IngredientResponseDto response = this.service.create(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<IngredientResponseDto>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.service.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<IngredientResponseDto> updateById(
        @PathVariable UUID id,
        @Valid @RequestBody IngredientUpdateRequestDto requestDto
    ){
        return ResponseEntity.ok(this.service.updateById(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        this.service.deleteById(id);
        return ResponseEntity.ok("Ingredient with ID " + id + " deleted successfully.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        this.service.deleteAll();
        return ResponseEntity.ok("All ingredients deleted successfully.");
    }
}
