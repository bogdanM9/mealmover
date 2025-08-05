package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.ClientConstants;
import mealmover.backend.dtos.requests.AddressCreateRequestDto;
import mealmover.backend.dtos.requests.AddressUpdateRequestDto;
import mealmover.backend.dtos.requests.CreditCardCreateRequestDto;
import mealmover.backend.dtos.responses.AddressResponseDto;
import mealmover.backend.dtos.responses.ClientResponseDto;
import mealmover.backend.dtos.responses.CreditCardResponseDto;
import mealmover.backend.dtos.responses.OAuth2UserInfoDto;
import mealmover.backend.enums.Role;
import mealmover.backend.exceptions.ConflictException;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.exceptions.UnauthorizedException;
import mealmover.backend.mapper.AddressMapper;
import mealmover.backend.mapper.ClientMapper;
import mealmover.backend.mapper.CreditCardMapper;
import mealmover.backend.models.AddressModel;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.CreditCardModel;
import mealmover.backend.models.RoleModel;
import mealmover.backend.repositories.ClientRepository;
import mealmover.backend.security.SecurityService;
import mealmover.backend.services.data.UserDataService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final RoleService roleService;
    private final ClientMapper clientMapper;
    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;
    private final UserDataService userDataService;
    private final ClientRepository clientRepository;
    private final CreditCardMapper creditCardMapper;
    private final CreditCardService creditCardService;

    public ClientModel create(ClientModel model) {
        String email = model.getEmail();

        log.info("Attempting to create a client with email: {}", email);

        if (this.userDataService.existsByEmail(email)) {
            throw new ConflictException(ClientConstants.ALREADY_EXISTS_BY_EMAIL);
        }

        RoleModel roleModel = this.roleService.getModelByName(Role.CLIENT.toCapitalize());

        model.getRoles().add(roleModel);

        ClientModel savedClientModel = this.clientRepository.save(model);

        log.info("Successfully created client with name: {}", model.getEmail());

        return savedClientModel;
    }

    @Transactional
    public ClientModel getOrCreateOAuth2Client(OAuth2UserInfoDto userInfoDto) {
        String email = userInfoDto.getEmail();

        log.info("Getting or creating OAuth2 client with email: {}", email);

        return this.clientRepository
            .findByEmail(email)
            .orElseGet(() -> createOAuth2Client(userInfoDto));
    }

    private ClientModel createOAuth2Client(OAuth2UserInfoDto userInfoDto) {
        log.info("Creating new OAuth2 client with email: {}", userInfoDto.getEmail());

        ClientModel client = new ClientModel();

        client.setEmail(userInfoDto.getEmail());
        client.setLastName(userInfoDto.getLastName());
        client.setFirstName(userInfoDto.getFirstName());

        client.setPassword(this.passwordEncoder.encode(UUID.randomUUID().toString()));

        RoleModel roleModel = this.roleService.getModelByName(Role.CLIENT.toCapitalize());

        client.addRole(roleModel);

        return this.clientRepository.save(client);
    }

    public List<ClientResponseDto> getAll() {
        log.info("Getting all clients");
        return this.clientRepository
            .findAll()
            .stream()
            .map(this.clientMapper::toDto)
            .toList();
    }

    public ClientResponseDto getById(UUID id) {
        log.info("Getting client by id: {}", id);
        return this.clientRepository
            .findById(id)
            .map(this.clientMapper::toDto)
            .orElseThrow(() -> new NotFoundException(ClientConstants.NOT_FOUND_BY_ID));
    }

    public void deleteByIdAddress(UUID addressId) {
        log.info("Attempting to delete address with id: {}", addressId);

        this.addressService.deleteById(addressId);

        log.info("Successfully deleted address with id: {}", addressId);
    }

    public void deleteByIdCreditCard(UUID creditCardId) {
        log.info("Attempting to delete credit card with id: {}", creditCardId);

        this.creditCardService.deleteById(creditCardId);

        log.info("Successfully deleted credit card with id: {}", creditCardId);
    }

    public AddressResponseDto updateByIdAddress(UUID addressId, AddressUpdateRequestDto requestDto) {
        log.info("Attempting to update address with id: {}", addressId);

        AddressModel addressModel = this.addressService.getModelById(addressId);

        addressModel.setCity(requestDto.getCity());
        addressModel.setStreet(requestDto.getStreet());
        addressModel.setNumber(requestDto.getNumber());
        addressModel.setCounty(requestDto.getCounty());
        addressModel.setDetails(requestDto.getDetails());
        addressModel.setPostcode(requestDto.getPostcode());

        AddressModel updatedAddress = this.addressService.update(addressModel);

        log.info("Successfully updated address with id: {}", addressId);

        return this.addressMapper.toDto(updatedAddress);
    }

    public CreditCardResponseDto updateByIdCreditCard(UUID creditCardId, CreditCardCreateRequestDto requestDto) {
        log.info("Attempting to update credit card with id: {}", creditCardId);

        CreditCardModel creditCardModel = this.creditCardService.getModelById(creditCardId);

        creditCardModel.setCardNumber(requestDto.getCardNumber());
        creditCardModel.setHolderName(requestDto.getHolderName());
        creditCardModel.setExpirationMonth(requestDto.getExpirationMonth());
        creditCardModel.setExpirationYear(requestDto.getExpirationYear());
        creditCardModel.setSecurityCode(requestDto.getSecurityCode());

        CreditCardModel updatedCreditCard = this.creditCardService.update(creditCardModel);

        log.info("Successfully updated credit card with id: {}", creditCardId);

        return this.creditCardMapper.toDto(updatedCreditCard);
    }

    public AddressResponseDto updateByIdAddressMain(UUID addressId) {
        log.info("Attempting to make address with id {} main", addressId);

        AddressModel addressModel = this.addressService.getModelById(addressId);

        ClientModel clientModel = (ClientModel)this.securityService.getModelCurrentUser();

        if (!addressModel.getClient().getId().equals(clientModel.getId())) {
            throw new UnauthorizedException(ClientConstants.NOT_FOUND_BY_ID);
        }

        Set<AddressModel> addresses = clientModel.getAddresses();

        for (AddressModel address : addresses) {
            if (address.getId() != addressId) {
                address.setMain(false);
                this.addressService.update(address);
            }
        }

        addressModel.setMain(true);

        AddressModel updatedAddress = this.addressService.update(addressModel);

        log.info("Successfully made address with id {} main", addressId);

        return this.addressMapper.toDto(updatedAddress);
    }

    public CreditCardResponseDto updateByIdCreditCardMain(UUID creditCardId) {
        log.info("Attempting to update credit card with id: {}", creditCardId);

        CreditCardModel creditCardModel = this.creditCardService.getModelById(creditCardId);

        ClientModel clientModel = (ClientModel)this.securityService.getModelCurrentUser();

        if (!creditCardModel.getClient().getId().equals(clientModel.getId())) {
            throw new UnauthorizedException(ClientConstants.NOT_FOUND_BY_ID);
        }

        Set<CreditCardModel> cards = clientModel.getCreditCards();

        for (CreditCardModel card : cards) {
            if (card.getId() != creditCardId) {
                card.setMain(false);
                this.creditCardService.update(card);
            }
        }

        creditCardModel.setMain(true);

        CreditCardModel updatedCreditCard = this.creditCardService.update(creditCardModel);

        log.info("Successfully updated credit card with id: {}", creditCardId);

        return this.creditCardMapper.toDto(updatedCreditCard);
    }

    public AddressResponseDto addAddress(AddressCreateRequestDto requestDto) {
        log.info("Attempting to add address to client");

        ClientModel clientModel = (ClientModel)this.securityService.getModelCurrentUser();

        AddressModel addressModel = this.addressMapper.toModel(requestDto);

        addressModel.setMain(clientModel.getAddresses().size() == 0);
        addressModel.setClient(clientModel);

        AddressModel createdModel = this.addressService.create(addressModel);

        log.info("Successfully added address to client");

        return this.addressMapper.toDto(createdModel);
    }

    public CreditCardResponseDto addCreditCard(CreditCardCreateRequestDto requestDto) {
        log.info("Attempting to add credit card to client");

        ClientModel clientModel = (ClientModel)this.securityService.getModelCurrentUser();

        CreditCardModel creditCardModel = this.creditCardMapper.toModel(requestDto);

        creditCardModel.setMain(clientModel.getCreditCards().size() == 0);

        creditCardModel.setClient(clientModel);

        CreditCardModel createdModel = this.creditCardService.create(creditCardModel);

        log.info("Successfully added credit card to client");

        return this.creditCardMapper.toDto(createdModel);

    }

    public void deleteAll() {
        log.info("Deleting all clients");
        this.clientRepository.deleteAll();
        log.info("Successfully deleted all clients");
    }

    public ClientModel getModelById(UUID clientId) {
        log.info("Getting client by id: {}", clientId);
        return this.clientRepository
            .findById(clientId)
            .orElseThrow(() -> {
                log.error("Client with id {} not found", clientId);
                return new NotFoundException(ClientConstants.NOT_FOUND_BY_ID);
            });
    }
}