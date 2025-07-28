package mealmover.backend.services.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.PendingClientConstants;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.models.PendingClientModel;
import mealmover.backend.repositories.PendingClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendingClientDataService {
    private final PendingClientRepository pendingClientRepository;

    @Transactional
    public PendingClientModel create(PendingClientModel model) {
        String email = model.getEmail();

        log.info("Attempting to create pending patient with email {}", email);

        if (this.pendingClientRepository.existsByEmail(email)) {
            throw new ConflictException(PendingClientConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        PendingClientModel savedModel = this.pendingClientRepository.save(model);

        log.info("Successfully created pending patient with email {}", email);

        return savedModel;
    }

    @Transactional(readOnly = true)
    public PendingClientModel getById(UUID id) {
        log.info("Getting pending patient with id {}", id);
        return this.pendingClientRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(PendingClientConstants.NOT_FOUND_BY_ID));
    }

    @Transactional(readOnly = true)
    public PendingClientModel getByEmail(String email) {
        log.info("Getting pending patient with email {}", email);
        return this.pendingClientRepository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(PendingClientConstants.NOT_FOUND_BY_EMAIL));
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        log.info("Checking if pending patient exists with email {}", email);
        return this.pendingClientRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean existsByPhoneNumber(String phoneNumber) {
        log.info("Checking if pending patient exists with phone number {}", phoneNumber);
        return this.pendingClientRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public void deleteById(UUID id) {
        log.info("Deleting pending patient with id {}", id);

        if (!this.pendingClientRepository.existsById(id)) {
            throw new NotFoundException(PendingClientConstants.NOT_FOUND_BY_ID);
        }

        this.pendingClientRepository.deleteById(id);

        log.info("Successfully deleted pending patient with id {}", id);
    }
}
