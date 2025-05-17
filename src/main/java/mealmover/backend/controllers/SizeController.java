//package mealmover.backend.controllers;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import mealmover.backend.dtos.requests.CreateSizeRequestDto;
//import mealmover.backend.dtos.responses.SizeResponseDto;
//import mealmover.backend.messages.SizeMessages;
//import mealmover.backend.services.SizeService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/sizes")
//@RequiredArgsConstructor
//public class SizeController {
//
//    private final SizeService service;
//    private final SizeMessages messages;
//
//    @PostMapping
//    public ResponseEntity<SizeResponseDto> create(@Valid @RequestBody CreateSizeRequestDto requestDto) {
//        SizeResponseDto response = this.service.create(requestDto);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<SizeResponseDto>> getAll() {
//        return ResponseEntity.ok(this.service.getAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<SizeResponseDto> getById(@PathVariable UUID id) {
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
//
//}
