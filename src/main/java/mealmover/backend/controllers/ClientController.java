package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.AddressCreateRequestDto;
import mealmover.backend.dtos.requests.AddressUpdateRequestDto;
import mealmover.backend.dtos.requests.CreditCardCreateRequestDto;
import mealmover.backend.dtos.responses.AddressResponseDto;
import mealmover.backend.dtos.responses.ClientResponseDto;
import mealmover.backend.dtos.responses.CreditCardResponseDto;
import mealmover.backend.dtos.responses.MessageResponseDto;
import mealmover.backend.services.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/credit-cards")
    public ResponseEntity<CreditCardResponseDto> addCreditCard(
        @Valid @RequestBody CreditCardCreateRequestDto requestDto
    ) {
        CreditCardResponseDto responseDto = this.clientService.addCreditCard(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/credit-cards/{id}")
    public ResponseEntity<CreditCardResponseDto> updateByIdCreditCard(
        @PathVariable UUID id,
        @Valid @RequestBody CreditCardCreateRequestDto requestDto
    ) {
        CreditCardResponseDto responseDto = this.clientService.updateByIdCreditCard(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/addresses/{id}/main")
    public ResponseEntity<AddressResponseDto> updateMainAddress(
        @PathVariable UUID id
    ) {
        AddressResponseDto responseDto = this.clientService.updateByIdAddressMain(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/credit-cards/{id}/main")
    public ResponseEntity<CreditCardResponseDto> updateMainCreditCard(
        @PathVariable UUID id
    ) {
        CreditCardResponseDto responseDto = this.clientService.updateByIdCreditCardMain(id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("credit-cards/{id}")
    public ResponseEntity<MessageResponseDto> deleteByIdCreditCard(@PathVariable UUID id) {
        this.clientService.deleteByIdCreditCard(id);
        MessageResponseDto responseDto = MessageResponseDto.success("Credit card deleted successfully.");
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/addresses")
    public ResponseEntity<AddressResponseDto> addAddress(
        @Valid @RequestBody AddressCreateRequestDto requestDto
    ) {
        AddressResponseDto responseDto = this.clientService.addAddress(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/addresses/{id}")
    public ResponseEntity<AddressResponseDto> updateAddress(
        @PathVariable UUID id,
        @Valid @RequestBody AddressUpdateRequestDto requestDto
    ) {
        AddressResponseDto responseDto = this.clientService.updateByIdAddress(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<MessageResponseDto> deleteAddress(@PathVariable UUID id) {
        this.clientService.deleteByIdAddress(id);
        MessageResponseDto responseDto = MessageResponseDto.success("Address deleted successfully.");
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<MessageResponseDto> deleteAll() {
        this.clientService.deleteAll();
        MessageResponseDto responseDto = MessageResponseDto.success("All clients deleted successfully.");
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public List<ClientResponseDto> getAll() {
        return this.clientService.getAll();
    }
}