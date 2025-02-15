package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.DriverCreateRequestDto;
import mealmover.backend.dtos.requests.OperatorCreateRequestDto;
import mealmover.backend.dtos.requests.OperatorUpdateRequestDto;
import mealmover.backend.dtos.responses.DriverResponseDto;
import mealmover.backend.dtos.responses.OperatorResponseDto;
import mealmover.backend.messages.DriverMessages;
import mealmover.backend.messages.OperatorMessages;
import mealmover.backend.services.DriverService;
import mealmover.backend.services.OperatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/drivers")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService service;
    private final DriverMessages messages;


    @PostMapping
    public ResponseEntity<DriverResponseDto> create(@Valid @RequestBody DriverCreateRequestDto requestDto) {
        DriverResponseDto response = this.service.create(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DriverResponseDto>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDto> getById(@PathVariable UUID id) {
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
