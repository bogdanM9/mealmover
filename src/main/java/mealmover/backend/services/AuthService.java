package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.AuthActivateClientRequestDto;
import mealmover.backend.dtos.AuthRegisterClientRequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PendingClientService pendingClientService;

    public void registerClient(AuthRegisterClientRequestDto requestDto) {
        pendingClientService.create(requestDto);
    }

    public void activateClient(AuthActivateClientRequestDto requestDto) {
        pendingClientService.activate(requestDto);
    }
}
