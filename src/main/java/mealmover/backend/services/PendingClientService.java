package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.CreatePendingClientRequestDto;
import mealmover.backend.dtos.responses.PendingClientResponseDto;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.PendingClientMapper;
import mealmover.backend.messages.PendingClientMessages;
import mealmover.backend.messages.UserMessages;
import mealmover.backend.models.PendingClientModel;
import mealmover.backend.repositories.PendingClientRepository;
import mealmover.backend.services.auth.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PendingClientService {
    private final JwtService jwtService;
    private final UserService userService;
    private final UserMessages userMessages;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final PendingClientMapper mapper;
    private final PendingClientMessages pendingClientMessages;
    private final PendingClientRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(PendingClientService.class);

    public PendingClientResponseDto create(CreatePendingClientRequestDto requestDto) {
        String email = requestDto.getEmail();

        logger.info("Attempting to create an Pending client with email: {}", email);

        if (this.repository.findByEmail(email).isPresent()) {
            throw new ConflictException(this.pendingClientMessages.alreadyExistsByEmail());
        }

        if(this.userService.existsByEmail(email)) {
            throw new ConflictException(this.userMessages.alreadyExistsByEmail(email));
        }

        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());

        PendingClientModel pendingClientModel = this.mapper.toModel(requestDto);
        pendingClientModel.setPassword(hashedPassword);

        UserDetails userDetails = this.mapper.toUserDetails(pendingClientModel);

        String token = this.jwtService.generateActivateToken(userDetails);

        pendingClientModel.setToken(token);

        PendingClientModel savedPendingClientModel = this.repository.save(pendingClientModel);

        logger.info("Successfully created pending client with email: {}", email);

        System.out.println(token);

        this.emailService.sendActivateAccountEmail(email, token);

        return this.mapper.toDto(savedPendingClientModel);
    }

    public List<PendingClientResponseDto> getAll() {
        logger.info("Getting all pending clients");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public PendingClientResponseDto getById(UUID id) {
        logger.info("Getting pending client by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(pendingClientMessages.notFoundById()));
    }



    public void deleteById(UUID id) {
        logger.info("Getting pending clients by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(pendingClientMessages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("pending client with id {} deleted!", id);
    }

    public void deleteAll() {
        logger.info("Deleting all pending clients..");
        this.repository.deleteAll();
        logger.info("pending clients!");
    }
}
