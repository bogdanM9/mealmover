package mealmover.backend.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

//    @PostMapping
//    public ResponseEntity<UserResponseDto> create(@Valid @RequestBody UserCreateRequestDto requestDto) {
//        log.info("Creating user with email {}", requestDto.getEmail());
//        UserResponseDto responseDto = this.service.create(requestDto);
//        return ResponseEntity.ok(responseDto);
//    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<UserResponseDto> getById(@PathVariable UUID id) {
//        return ResponseEntity.ok(this.service.getById(id));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
//        this.service.deleteById(id);
//        return ResponseEntity.ok(GeneralConstants.USER_DELETED_MESSAGE);
//    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        this.service.deleteAll();
        return ResponseEntity.ok("All Users have been deleted");
    }
}
