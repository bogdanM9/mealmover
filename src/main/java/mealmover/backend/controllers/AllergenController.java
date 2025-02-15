package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.AllergenCreateRequestDto;
import mealmover.backend.dtos.responses.AllergenResponseDto;
import mealmover.backend.messages.AllergenMessages;
import mealmover.backend.services.AllergenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/allergens")
@RequiredArgsConstructor
public class AllergenController {

    private final AllergenService service;
    private final AllergenMessages messages;

    @PostMapping
    public ResponseEntity<AllergenResponseDto> create(@Valid @RequestBody AllergenCreateRequestDto requestDto) {
        AllergenResponseDto response = this.service.create(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AllergenResponseDto>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllergenResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.service.getById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        this.service.deleteById(id);
        return ResponseEntity.ok(messages.deleted());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        this.service.deleteAll();
        return ResponseEntity.ok(messages.deletedAll());
    }

}
