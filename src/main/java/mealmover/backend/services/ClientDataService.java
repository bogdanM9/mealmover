package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.ClientConstants;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.models.ClientModel;
import mealmover.backend.repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientDataService {
    private final ClientRepository clientRepository;

    public ClientModel getById(UUID id) {
        log.info("Getting client by id: {}", id);
        return this.clientRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(ClientConstants.NOT_FOUND_BY_ID));
    }
}
