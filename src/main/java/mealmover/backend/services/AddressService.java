package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.AddressCreateRequestDto;
import mealmover.backend.dtos.responses.AddressResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.AddressMapper;
import mealmover.backend.messages.AddressMessages;
import mealmover.backend.models.AddressModel;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.CreditCardModel;
import mealmover.backend.repositories.AddressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper mapper;
    private final AddressMessages messages;
    private final AddressRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);


    @Transactional
    public AddressResponseDto create(AddressCreateRequestDto dto) {
        logger.info("Attempting to create an Address");

        AddressModel address = this.mapper.toModel(dto);

        AddressModel savedAddress = this.repository.save(address);

        logger.info("Successfully created address");

        return this.mapper.toDto(savedAddress);
    }

    @Transactional
    public AddressResponseDto create(ClientModel clientModel, AddressCreateRequestDto requestDto) {
        logger.info("Attempting to create an Address");

        AddressModel addressModel = this.mapper.toModel(requestDto);
        addressModel.setClient(clientModel);

        AddressModel createdAddress = this.repository.save(addressModel);

        logger.info("Successfully created address");

        return this.mapper.toDto(createdAddress);
    }

    @Transactional
    public AddressModel save(ClientModel clientModel, AddressCreateRequestDto requestDto) {
        logger.info("Attempting to create an Address");

        AddressModel addressModel = this.mapper.toModel(requestDto);
        addressModel.setClient(clientModel);

        AddressModel createdAddress = this.repository.save(addressModel);

        logger.info("Successfully created address");

        return createdAddress;
    }


    @Transactional(readOnly = true)
    public List<AddressResponseDto> getAll() {
        logger.info("Getting all Addresses");
        return this.repository
            .findAll()
            .stream()
            .map(this.mapper::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public AddressResponseDto getById(UUID id) {
        logger.info("Getting address by id: {}", id);
        return this.repository
            .findById(id)
            .map(this.mapper::toDto)
            .orElseThrow(() -> new NotFoundException(this.messages.notFoundById()));
    }

    @Transactional
    public void deleteById(UUID id) {
        logger.info("Getting Address by id: {}", id);
        this.repository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        logger.info("Deleting all Addresses..");
        this.repository.deleteAll();
        logger.info("Addresses deleted!");
    }

    @Transactional(readOnly = true)
    public AddressModel getModelById(UUID addressId) {
        return this.repository.findById(addressId)
            .orElseThrow(() -> new NotFoundException(this.messages.notFoundById()));
    }

    public void delete(ClientModel client, UUID addressId) {
        logger.info("Attempting to delete an Address");

        AddressModel address = this.repository
            .findById(addressId)
            .orElseThrow();

        if (!address.getClient().equals(client)) {
            throw new RuntimeException("Address does not belong to client");
        }

        this.repository.delete(address);

        logger.info("Successfully deleted address");

    }
}
