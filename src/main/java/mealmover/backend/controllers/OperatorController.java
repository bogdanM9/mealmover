package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.OperatorCreateRequestDto;
import mealmover.backend.dtos.requests.OperatorUpdateRequestDto;
import mealmover.backend.dtos.responses.OperatorResponseDto;
import mealmover.backend.messages.OperatorMessages;
import mealmover.backend.services.OperatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService service;
    private final OperatorMessages messages;


    @PostMapping
    public ResponseEntity<OperatorResponseDto> create(@Valid @RequestBody OperatorCreateRequestDto requestDto) {
        OperatorResponseDto response = this.service.create(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<OperatorResponseDto>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorResponseDto> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.service.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OperatorResponseDto> updateById(
            @PathVariable UUID id,
            @Valid @RequestBody OperatorUpdateRequestDto requestDto
    ){

        return ResponseEntity.ok(this.service.updateById(id, requestDto));
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
