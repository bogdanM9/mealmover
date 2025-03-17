package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.CreditCardCreateRequestDto;
import mealmover.backend.dtos.responses.CreditCardResponseDto;
import mealmover.backend.mapper.CreditCardMapper;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.CreditCardModel;
import mealmover.backend.repositories.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardMapper creditCardMapper;
    private final CreditCardRepository creditCardRepository;

    private static final Logger logger = LoggerFactory.getLogger(CreditCardService.class);

    private final PasswordEncoder passwordEncoder;

    public CreditCardResponseDto create(CreditCardCreateRequestDto dto, ClientModel client) {
        logger.info("Attempting to create a CreditCard");

        CreditCardModel creditCard = this.creditCardMapper.toModel(dto);
        creditCard.setClient(client);

        String hashedCardNumber = passwordEncoder.encode(dto.getCardNumber());
        creditCard.setCardNumber(hashedCardNumber);

        CreditCardModel savedCreditCard = this.creditCardRepository.save(creditCard);

        logger.info("Successfully created creditCard");

        return this.creditCardMapper.toDto(savedCreditCard);
    }


    public List<CreditCardResponseDto> getAllCreditCards() {
          logger.info("Getting all CreditCards");
          return this.creditCardRepository
                .findAll()
                .stream()
                .map(this.creditCardMapper::toDto)
                .toList();
    }

    public void delete(ClientModel client, UUID creditCardId) {
        logger.info("Attempting to delete a CreditCard");

        CreditCardModel creditCard = this.creditCardRepository
                .findById(creditCardId)
                .orElseThrow();

        if (!creditCard.getClient().equals(client)) {
            throw new RuntimeException("CreditCard does not belong to client");
        }

        this.creditCardRepository.delete(creditCard);

        logger.info("Successfully deleted CreditCard");
    }
}
