package mealmover.backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.PendingClientConstants;
import mealmover.backend.dtos.responses.PendingClientResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.PendingClientMapper;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.PendingClientModel;
import mealmover.backend.repositories.PendingClientRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendingClientService {
    private final UserService userService;
    private final EmailService emailService;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final PendingClientMapper pendingClientMapper;
    private final PendingClientRepository pendingClientRepository;

//    public void create(PendingClientCreateRequestDto requestDto) {
//        String email = requestDto.getEmail();
//
//        logger.info("Attempting to create an Pending client with email: {}", email);
//
//        if (this.repository.findByEmail(email).isPresent()) {
//            throw new ConflictException(this.pendingClientMessages.alreadyExistsByEmail());
//        }
//
//        if(this.userService.existsByEmail(email)) {
//            throw new ConflictException(this.userMessages.alreadyExistsByEmail(email));
//        }
//
//        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
//
//        PendingClientModel pendingClientModel = this.mapper.toModel(requestDto);
//        pendingClientModel.setPassword(hashedPassword);
//
//        UserDetails userDetails = this.mapper.toUserDetails(pendingClientModel);
//
//        String token = this.jwtService.generateActivateClientToken(userDetails);
//
//        pendingClientModel.setToken(token);
//
//        this.repository.save(pendingClientModel);
//
//        logger.info("Successfully created pending client with email: {}", email);
//
//        this.emailService.sendActivateAccountEmail(email, token);
//
//    }

    @Transactional
    public PendingClientModel create(PendingClientModel model) {
        String email = model.getEmail();

        log.info("Attempting to create pending client with email {}", email);

        if(this.pendingClientRepository.existsByEmail(email)) {
            throw new ConflictException("There is already an pending client registered with that email.");
        }

        PendingClientModel savedModel = this.pendingClientRepository.save(model);

        log.info("Successfully created pending client with email {}", email);

        return savedModel;
    }

//    public void activate(AuthActivateClientRequestDto token) {
//        log.info("Attempting to activate pending client with token: {}", token);
//
//        PendingClientModel pendingClientModel = this.repository
//            .findByToken(token.getToken())
//            .orElseThrow(() -> new NotFoundException(this.pendingClientMessages.notFoundByToken()));
//
//        ClientModel clientModel = this.mapper.toClientModel(pendingClientModel);
//
//        this.clientService.create(clientModel);
//
//        this.repository.deleteById(pendingClientModel.getId());
//
//        log.info("Successfully activated pending client with token: {}", token);
//    }

    public boolean existsByEmail(String email) {
        return this.pendingClientRepository.existsByEmail(email);
    }

    public PendingClientModel getModelByEmail(String email) {
        return this.pendingClientRepository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(PendingClientConstants.NOT_FOUND_BY_EMAIL));
    }

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 10000 * 30)
    public void deleteOldPendingClients() {
        log.info("Deleting old pending clients..");

//        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(5);

        int deletedCount = this.pendingClientRepository.deleteByCreatedAtBefore(cutoffTime);

        log.info("Deleted {} old pending clients", deletedCount);
    }

    public List<PendingClientResponseDto> getAll() {
        log.info("Getting all pending clients");
        return this.pendingClientRepository
            .findAll()
            .stream()
            .map(this.pendingClientMapper::toDto)
            .toList();
    }

    public PendingClientResponseDto getById(UUID id) {
        log.info("Getting pending client by id: {}", id);
        return this.pendingClientRepository
            .findById(id)
            .map(this.pendingClientMapper::toDto)
            .orElseThrow(() -> new NotFoundException(PendingClientConstants.NOT_FOUND_BY_NAME));
    }

    public void deleteById(UUID id) {
        log.info("Getting pending clients by id: {}", id);

        if (this.pendingClientRepository.findById(id).isEmpty()) {
            throw new NotFoundException(PendingClientConstants.NOT_FOUND_BY_NAME);
        }

        this.pendingClientRepository.deleteById(id);

        log.info("pending client with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all pending clients..");
        this.pendingClientRepository.deleteAll();
        log.info("pending clients!");
    }
}
