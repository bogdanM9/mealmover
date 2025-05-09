package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.AddressCreateRequestDto;
import mealmover.backend.dtos.requests.ClientCreateRequestDto;
import mealmover.backend.dtos.requests.ClientUpdateRequestDto;
import mealmover.backend.dtos.requests.CreditCardCreateRequestDto;
import mealmover.backend.dtos.responses.AddressResponseDto;
import mealmover.backend.dtos.responses.ClientResponseDto;
import mealmover.backend.dtos.responses.CreditCardResponseDto;
import mealmover.backend.messages.ClientMessages;
import mealmover.backend.messages.CreditCardMessages;
import mealmover.backend.services.ClientService;
import mealmover.backend.services.CreditCardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;
    private final ClientMessages messages;

    @PostMapping
    public ResponseEntity<ClientResponseDto> create(@Valid @RequestBody ClientCreateRequestDto requestDto) {
        ClientResponseDto response = this.service.create(requestDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-address")
    public ResponseEntity<AddressResponseDto> addAddress(
        @Valid @RequestBody AddressCreateRequestDto requestDto
    ) {
        return ResponseEntity.ok(this.service.addAddress(requestDto));
    }

    @GetMapping
    public ResponseEntity<List<ClientResponseDto>> getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping("/client-info")
        public ResponseEntity<ClientResponseDto> getClient() {
        return ResponseEntity.ok(this.service.getClient());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponseDto> updateById(
        @PathVariable UUID id,
        @Valid @RequestBody ClientUpdateRequestDto requestDto
    ){
        return ResponseEntity.ok(this.service.updateById(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable UUID id) {
        this.service.deleteById(id);
        return ResponseEntity.ok(messages.deleted());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAll() {
        this.service.deleteAll();
        return ResponseEntity.ok(messages.deletedAll());
    }

    @PostMapping("/add/credit-cards")
    public ResponseEntity<CreditCardResponseDto> create(@Valid @RequestBody CreditCardCreateRequestDto requestDto) {
        CreditCardResponseDto response = this.service.addCreditCard(requestDto);
        return ResponseEntity.ok(response);
    }
}