package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.AddressCreateRequestDto;
import mealmover.backend.dtos.requests.ClientCreateRequestDto;
import mealmover.backend.dtos.requests.ClientUpdateRequestDto;
import mealmover.backend.dtos.requests.CreditCardCreateRequestDto;
import mealmover.backend.dtos.responses.AddressResponseDto;
import mealmover.backend.dtos.responses.ClientResponseDto;
import mealmover.backend.dtos.responses.CreditCardResponseDto;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.AddressMapper;
import mealmover.backend.mapper.ClientMapper;
import mealmover.backend.messages.ClientMessages;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.RoleModel;
import mealmover.backend.repositories.ClientRepository;
import mealmover.backend.security.SecurityUtils;
import mealmover.backend.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final AddressService addressService;

    private final AddressMapper addressMapper;
    private final ClientMapper mapper;
    private final ClientMessages messages;
    private final RoleService roleService;
    private final UserService userService;
    private final ClientRepository repository;
    private final SecurityUtils securityUtils;
    private final CreditCardService creditCardService;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    public ClientResponseDto create(ClientCreateRequestDto requestDto) {
        String email = requestDto.getEmail();

        logger.info("Attempting to create a clients with email: {}", email);

        if (this.userService.existsByEmail(email)) {
            throw new ConflictException(this.messages.alreadyExistsByEmail());
        }

        RoleModel roleModel = this.roleService.getOrCreate(Role.CLIENT.toConvert());

        ClientModel clientModel = this.mapper.toModel(requestDto);

        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());

        clientModel.getRoles().add(roleModel);
        clientModel.setPassword(hashedPassword);

        ClientModel savedClientModel = this.repository.save(clientModel);

        logger.info("Successfully created client with name: {}", email);

        return this.mapper.toDto(savedClientModel);
    }

    public ClientResponseDto create(ClientModel clientModel) {
        logger.info("Attempting to create a clients with email: {}", clientModel.getEmail());

        if (this.userService.existsByEmail(clientModel.getEmail())) {
            throw new ConflictException(this.messages.alreadyExistsByEmail());
        }

        RoleModel roleModel = this.roleService.getOrCreate(Role.CLIENT.toConvert());

        clientModel.getRoles().add(roleModel);

        System.out.println(clientModel);

        ClientModel savedClientModel = this.repository.save(clientModel);

        logger.info("Successfully created client with name: {}", clientModel.getEmail());

        return this.mapper.toDto(savedClientModel);
    }

    public List<ClientResponseDto> getAll() {
        logger.info("Getting all clients");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    public ClientResponseDto getById(UUID id) {
        logger.info("Getting client by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }

    public ClientResponseDto getClient() {
        logger.info("Getting client info...");
        UserDetailsImpl userDetailsImpl = this.securityUtils.getCurrentUser();
        return this.repository
            .findByEmail(userDetailsImpl.getUsername())
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(messages.notFoundByEmail()));
    }

    public ClientModel getModelById(UUID id) {
        logger.info("Getting client by id: {}", id);
        return this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
    }

    public ClientResponseDto updateById(UUID id, ClientUpdateRequestDto requestDto) {
        logger.info("Attempting to update client with id: {}", id);

        ClientModel client = this.repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));

        client.setLastName(requestDto.getLastName());
        client.setFirstName(requestDto.getFirstName());
        client.setPhoneNumber(requestDto.getPhoneNumber());

        ClientModel updatedClient = this.repository.save(client);

        logger.info("Successfully update client with id: {}", id);

        return this.mapper.toDto(updatedClient);
    }

    public void deleteById(UUID id) {
        logger.info("Getting clients by id: {}", id);

        if (this.repository.findById(id).isEmpty()) {
            throw new NotFoundException(messages.notFoundById());
        }

        this.repository.deleteById(id);

        logger.info("Client with id {} deleted!", id);
    }


    public AddressResponseDto addAddress( AddressCreateRequestDto addressCreateRequestDto) {
        logger.info("Attempting to add address to client");

        String email = this.securityUtils.getCurrentUser().getUsername();
        ClientModel loggedInClient = this.repository.findByEmail(email)
            .orElseThrow(() -> new NotFoundException(messages.notFoundByEmail()));

        AddressResponseDto responseDto = this.addressService.create(loggedInClient, addressCreateRequestDto);

        logger.info("Successfully added address with email: {}", email);

        return responseDto;
    }

    public CreditCardResponseDto addCreditCard(CreditCardCreateRequestDto creditCardDto) {
        ClientModel client = this.repository.findById(creditCardDto.getClientId())
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
        return this.creditCardService.create(creditCardDto, client);
    }

    public void deleteCreditCard(UUID clientId, UUID creditCardId) {
        ClientModel client = this.repository.findById(clientId)
                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
        this.creditCardService.delete(client, creditCardId);
    }

    public void deleteAddress(UUID clientId, UUID addressId) {
        ClientModel client = this.repository.findById(clientId)
            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
        this.addressService.delete(client, addressId);
    }

    public void deleteAll() {
        logger.info("Deleting all clients..");
        this.repository.deleteAll();
        logger.info("Clients deleted!");
    }



}