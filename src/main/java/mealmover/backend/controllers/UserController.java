//package mealmover.backend.controllers;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import mealmover.backend.dtos.requests.UserCreateRequestDto;
//import mealmover.backend.dtos.responses.UserResponseDto;
//import mealmover.backend.messages.RoleMessages;
//import mealmover.backend.services.UserService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//public class UserController {
//
//    private final UserService service;
//    private final RoleMessages roleMessages;
////
////    @PostMapping
////    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateRequestDto requestDto) {
////        UserResponseDto response = this.service.create(requestDto);
////        return ResponseEntity.ok(response);
////    }
//
//    @GetMapping
//    public ResponseEntity<List<UserResponseDto>> getAll() {
//        return ResponseEntity.ok(this.service.getAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id) {
//        return ResponseEntity.ok(this.service.getById(id));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
//        this.service.deleteById(id);
//        return ResponseEntity.ok(roleMessages.deleted());
//    }
//
//    @DeleteMapping
//    public ResponseEntity<String> deleteAll() {
//        this.service.deleteAll();
//        return ResponseEntity.ok("All Users have been deleted");
//    }
//}
