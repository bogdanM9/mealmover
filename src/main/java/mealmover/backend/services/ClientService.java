package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.ClientConstants;
import mealmover.backend.dtos.requests.AddressCreateRequestDto;
import mealmover.backend.dtos.responses.AddressResponseDto;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.AddressMapper;
import mealmover.backend.models.AddressModel;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.RoleModel;
import mealmover.backend.repositories.ClientRepository;
import mealmover.backend.security.SecurityService;
import mealmover.backend.security.SecurityUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final RoleService roleService;
    private final UserService userService;
    private final AddressMapper addressMapper;
    private final SecurityUtils securityUtils;
    private final AddressService addressService;
    private final SecurityService securityService;
    private final ClientRepository clientRepository;
    private final ClientDataService clientDataService;

//    public ClientResponseDto create(ClientCreateRequestDto requestDto) {
//        String email = requestDto.getEmail();
//
//        log.info("Attempting to create a clients with email: {}", email);
//
//        if (this.userService.existsByEmail(email)) {
//            throw new ConflictException(this.messages.alreadyExistsByEmail());
//        }
//
//        RoleModel roleModel = this.roleService.getOrCreate(Role.CLIENT.toConvert());
//
//        ClientModel clientModel = this.mapper.toModel(requestDto);
//
//        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
//
//        clientModel.getRoles().add(roleModel);
//        clientModel.setPassword(hashedPassword);
//
//        ClientModel savedClientModel = this.repository.save(clientModel);
//
//        logger.info("Successfully created client with name: {}", email);
//
//        return this.mapper.toDto(savedClientModel);
//    }
//
    public ClientModel create(ClientModel model) {
        String email = model.getEmail();

        log.info("Attempting to create a client with email: {}", email);

        if (this.userService.existsByEmail(email)) {
            throw new ConflictException(ClientConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        RoleModel roleModel = this.roleService.getModelByName(Role.CLIENT.toCapitalize());

        model.getRoles().add(roleModel);

        ClientModel savedClientModel = this.clientRepository.save(model);

        log.info("Successfully created client with name: {}", model.getEmail());

        return savedClientModel;
    }

    public ClientModel getModelByEmail(String email) {
        log.info("Getting client model by email: {}", email);
        return this.clientRepository
            .findByEmail(email)
            .orElseThrow(() -> new NotFoundException(ClientConstants.NOT_FOUND_BY_EMAIL));
    }
//
//    public List<ClientResponseDto> getAll() {
//        log.info("Getting all clients");
//        return this.repository
//            .findAll()
//            .stream()
//            .map(this.mapper::toDto)
//            .toList();
//    }

//    public ClientResponseDto getById(UUID id) {
//        logger.info("Getting client by id: {}", id);
//        return this.repository
//            .findById(id)
//            .map(this.mapper::toDto)
//            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
//    }

//    public ClientResponseDto getClient() {
//        logger.info("Getting client info...");
//        UserDetailsImpl userDetailsImpl = this.securityUtils.getCurrentUser();
//        return this.repository
//            .findByEmail(userDetailsImpl.getUsername())
//            .map(this.mapper::toDto)
//            .orElseThrow(() -> new NotFoundException(messages.notFoundByEmail()));
//    }
//
//    public ClientModel getModelById(UUID id) {
//        logger.info("Getting client by id: {}", id);
//        return this.repository
//                .findById(id)
//                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
//    }
//
//    public ClientResponseDto updateById(UUID id, ClientUpdateRequestDto requestDto) {
//        logger.info("Attempting to update client with id: {}", id);
//
//        ClientModel client = this.repository
//                .findById(id)
//                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
//
//        client.setLastName(requestDto.getLastName());
//        client.setFirstName(requestDto.getFirstName());
//        client.setPhoneNumber(requestDto.getPhoneNumber());
//
//        ClientModel updatedClient = this.repository.save(client);
//
//        logger.info("Successfully update client with id: {}", id);
//
//        return this.mapper.toDto(updatedClient);
//    }
//
//    public void deleteById(UUID id) {
//        logger.info("Getting clients by id: {}", id);
//
//        if (this.repository.findById(id).isEmpty()) {
//            throw new NotFoundException(messages.notFoundById());
//        }
//
//        this.repository.deleteById(id);
//
//        logger.info("Client with id {} deleted!", id);
//    }
//
//
    public AddressResponseDto addAddress( AddressCreateRequestDto requestDto) {
        log.info("Attempting to add address to client");

        ClientModel clientModel = (ClientModel)this.securityService.getModelCurrentUser();

        AddressModel addressModel = this.addressMapper.toModel(requestDto);

        addressModel.setClient(clientModel);

        AddressModel createdModel = this.addressService.create(addressModel);

        log.info("Successfully added address to client");

        return this.addressMapper.toDto(createdModel);
    }
//
//    public CreditCardResponseDto addCreditCard(CreditCardCreateRequestDto creditCardDto) {
//        ClientModel client = this.repository.findById(creditCardDto.getClientId())
//            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
//        return this.creditCardService.create(creditCardDto, client);
//    }
//
//    public void deleteCreditCard(UUID clientId, UUID creditCardId) {
//        ClientModel client = this.repository.findById(clientId)
//                .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
//        this.creditCardService.delete(client, creditCardId);
//    }
//
//    public void deleteAddress(UUID clientId, UUID addressId) {
//        ClientModel client = this.repository.findById(clientId)
//            .orElseThrow(() -> new NotFoundException(messages.notFoundById()));
//        this.addressService.delete(client, addressId);
//    }
//
//    public void deleteAll() {
//        logger.info("Deleting all clients..");
//        this.repository.deleteAll();
//        logger.info("Clients deleted!");
//    }
//
//

}