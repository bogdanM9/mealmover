package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.AuthActivateClientRequestDto;
import mealmover.backend.dtos.AuthRegisterClientRequestDto;
import mealmover.backend.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register-client")
    public ResponseEntity<String> registerClient(@Valid @RequestBody AuthRegisterClientRequestDto requestDto) {
        authService.registerClient(requestDto);
        return ResponseEntity.ok("CREATE LOGIN");
    }

    @PostMapping("/activate-client")
    public ResponseEntity<String> activateClient(@Valid @RequestBody AuthActivateClientRequestDto requestDto) {
        authService.activateClient(requestDto);
        return ResponseEntity.ok("ACTIVATE LOGIN");
    }
}