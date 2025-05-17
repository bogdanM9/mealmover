package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.AddressCreateRequestDto;
import mealmover.backend.dtos.responses.AddressResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.AddressMapper;
import mealmover.backend.models.AddressModel;
import mealmover.backend.models.ClientModel;
import mealmover.backend.repositories.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressMapper addressMapper;
    private final AddressRepository addressRepository;
    
    @Transactional
    public AddressModel create(AddressModel model) {
        log.info("Attempting to create an Address");

        AddressModel savedModel = this.addressRepository.save(model);

        log.info("Successfully created address");

        return savedModel;
    }

//    @Transactional
//    public AddressResponseDto create(ClientModel clientModel, AddressCreateRequestDto requestDto) {
//        log.info("Attempting to create an Address");
//
//        AddressModel addressModel = this.mapper.toModel(requestDto);
//        addressModel.setClient(clientModel);
//
//        AddressModel createdAddress = this.repository.save(addressModel);
//
//        log.info("Successfully created address");
//
//        return this.mapper.toDto(createdAddress);
//    }
//
//    @Transactional
//    public AddressModel save(ClientModel clientModel, AddressCreateRequestDto requestDto) {
//        log.info("Attempting to create an Address");
//
//        AddressModel addressModel = this.mapper.toModel(requestDto);
//        addressModel.setClient(clientModel);
//
//        AddressModel createdAddress = this.repository.save(addressModel);
//
//        log.info("Successfully created address");
//
//        return createdAddress;
//    }
//
//
//    @Transactional(readOnly = true)
//    public List<AddressResponseDto> getAll() {
//        log.info("Getting all Addresses");
//        return this.repository
//            .findAll()
//            .stream()
//            .map(this.mapper::toDto)
//            .toList();
//    }
//
//    @Transactional(readOnly = true)
//    public AddressResponseDto getById(UUID id) {
//        log.info("Getting address by id: {}", id);
//        return this.repository
//            .findById(id)
//            .map(this.mapper::toDto)
//            .orElseThrow(() -> new NotFoundException(this.messages.notFoundById()));
//    }
//
//    @Transactional
//    public void deleteById(UUID id) {
//        log.info("Getting Address by id: {}", id);
//        this.repository.deleteById(id);
//    }
//
//    @Transactional
//    public void deleteAll() {
//        log.info("Deleting all Addresses..");
//        this.repository.deleteAll();
//        log.info("Addresses deleted!");
//    }
//
//    @Transactional(readOnly = true)
//    public AddressModel getModelById(UUID addressId) {
//        return this.repository.findById(addressId)
//            .orElseThrow(() -> new NotFoundException(this.messages.notFoundById()));
//    }
//
//    public void delete(ClientModel client, UUID addressId) {
//        log.info("Attempting to delete an Address");
//
//        AddressModel address = this.repository
//            .findById(addressId)
//            .orElseThrow();
//
//        if (!address.getClient().equals(client)) {
//            throw new RuntimeException("Address does not belong to client");
//        }
//
//        this.repository.delete(address);
//
//        log.info("Successfully deleted address");
//
//    }
}
