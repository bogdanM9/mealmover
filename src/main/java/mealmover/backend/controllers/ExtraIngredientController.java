package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ExtraIngredientCreateRequestDto;
import mealmover.backend.dtos.responses.ExtraIngredientResponseDto;
import mealmover.backend.services.ExtraIngredientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/extra-ingredients")
@RequiredArgsConstructor
public class ExtraIngredientController {
    private final ExtraIngredientService service;
    @PostMapping
    public ResponseEntity<ExtraIngredientResponseDto> create(@Valid @RequestBody ExtraIngredientCreateRequestDto requestDto) {
        ExtraIngredientResponseDto response = this.service.create(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ExtraIngredientResponseDto>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtraIngredientResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        this.service.deleteById(id);
        return ResponseEntity.ok("Extra ingredient with ID " + id + " deleted successfully.");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        this.service.deleteAll();
        return ResponseEntity.ok("All extra ingredients deleted successfully.");
    }
}
