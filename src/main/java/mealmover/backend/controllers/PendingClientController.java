//package mealmover.backend.controllers;
//
//import lombok.RequiredArgsConstructor;
//import mealmover.backend.dtos.responses.PendingClientResponseDto;
//import mealmover.backend.messages.PendingClientMessages;
//import mealmover.backend.services.PendingClientService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/pending-clients")
//@RequiredArgsConstructor
//public class PendingClientController {
//    private final PendingClientService service;
//    private final PendingClientMessages messages;
//
////    @PostMapping
////    public ResponseEntity<PendingClientResponseDto> create(@Valid @RequestBody PendingClientCreateRequestDto requestDto) {
////        PendingClientResponseDto response = this.service.create(requestDto);
////        return ResponseEntity.ok(response);
////    }
//
//    @GetMapping
//    public ResponseEntity<List<PendingClientResponseDto>> getAll() {
//        return ResponseEntity.ok(this.service.getAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<PendingClientResponseDto> getById(@PathVariable UUID id) {
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
