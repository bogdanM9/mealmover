package mealmover.backend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.AuthActivateClientRequestDto;
import mealmover.backend.dtos.requests.PendingClientCreateRequestDto;
import mealmover.backend.dtos.responses.PendingClientResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.PendingClientMapper;
import mealmover.backend.messages.PendingClientMessages;
import mealmover.backend.messages.UserMessages;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.PendingClientModel;
import mealmover.backend.repositories.PendingClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserMessages userMessages;
    private final EmailService emailService;
    private final ClientService clientService;
    private final PasswordEncoder passwordEncoder;
    private final PendingClientMapper mapper;
    private final PendingClientMessages pendingClientMessages;
    private final PendingClientRepository repository;

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

        if(this.repository.existsByEmail(email)) {
            throw new ConflictException("There is already an pending client registered with that email.");
        }

        PendingClientModel savedModel = this.repository.save(model);

        log.info("Successfully created pending client with email {}", email);

        return savedModel;
    }

    public void activate(AuthActivateClientRequestDto token) {
        log.info("Attempting to activate pending client with token: {}", token);

        PendingClientModel pendingClientModel = this.repository
            .findByToken(token.getToken())
            .orElseThrow(() -> new NotFoundException(this.pendingClientMessages.notFoundByToken()));

        ClientModel clientModel = this.mapper.toClientModel(pendingClientModel);

        this.clientService.create(clientModel);

        this.repository.deleteById(pendingClientModel.getId());

        log.info("Successfully activated pending client with token: {}", token);
    }

    public boolean existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }

    public PendingClientModel getModelByEmail(String email) {
        return this.repository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(this.pendingClientMessages.notFoundByEmail()));
    }

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(fixedRate = 10000 * 30)
    public void deleteOldPendingClients() {
        log.info("Deleting old pending clients..");

//        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        LocalDateTime cutoffTime = LocalDateTime.now().minusMinutes(5);

        int deletedCount = this.repository.deleteByCreatedAtBefore(cutoffTime);

        log.info("Deleted {} old pending clients", deletedCount);
    }

    public List<PendingClientResponseDto> getAll() {
        log.info("Getting all pending clients");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public PendingClientResponseDto getById(UUID id) {
        log.info("Getting pending client by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(pendingClientMessages.notFoundById()));
    }

    public void deleteById(UUID id) {
        log.info("Getting pending clients by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(pendingClientMessages.notFoundById());
        }

        this.repository.deleteById(id);

        log.info("pending client with id {} deleted!", id);
    }

    public void deleteAll() {
        log.info("Deleting all pending clients..");
        this.repository.deleteAll();
        log.info("pending clients!");
    }
}
