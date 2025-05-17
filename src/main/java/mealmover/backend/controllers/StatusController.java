//package mealmover.backend.controllers;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import mealmover.backend.dtos.requests.StatusCreateRequestDto;
//import mealmover.backend.dtos.responses.StatusResponseDto;
//import mealmover.backend.messages.StatusMessages;
//import mealmover.backend.services.StatusService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/statuses")
//@RequiredArgsConstructor
//public class StatusController {
//
//    private final StatusService service;
//    private final StatusMessages messages;
//
//    @PostMapping
//    public ResponseEntity<StatusResponseDto> create(@Valid @RequestBody StatusCreateRequestDto requestDto) {
//        StatusResponseDto response = this.service.create(requestDto);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<StatusResponseDto>> getAll() {
//        return ResponseEntity.ok(this.service.getAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<StatusResponseDto> getById(@PathVariable UUID id) {
//        return ResponseEntity.ok(this.service.getById(id));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
//        this.service.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Void> deleteAll() {
//        this.service.deleteAll();
//        return ResponseEntity.noContent().build();
//    }
//}
