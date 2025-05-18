package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class CreditCardService {
    private final CreditCardMapper creditCardMapper;
    private final CreditCardRepository creditCardRepository;
    private final PasswordEncoder passwordEncoder;

//    public CreditCardResponseDto create(CreditCardCreateRequestDto dto, ClientModel client) {
//        logger.info("Attempting to create a CreditCard");
//
//        CreditCardModel creditCard = this.creditCardMapper.toModel(dto);
//        creditCard.setClient(client);
//
//        String hashedCardNumber = passwordEncoder.encode(dto.getCardNumber());
//        creditCard.setCardNumber(hashedCardNumber);
//
//        CreditCardModel savedCreditCard = this.creditCardRepository.save(creditCard);
//
//        logger.info("Successfully created creditCard");
//
//        return this.creditCardMapper.toDto(savedCreditCard);
//    }


    public List<CreditCardResponseDto> getAllCreditCards() {
        log.info("Getting all CreditCards");
          return this.creditCardRepository
                .findAll()
                .stream()
                .map(this.creditCardMapper::toDto)
                .toList();
    }

    public void delete(ClientModel client, UUID creditCardId) {
        log.info("Attempting to delete a CreditCard");

        CreditCardModel creditCard = this.creditCardRepository
            .findById(creditCardId)
            .orElseThrow();

        if (!creditCard.getClient().equals(client)) {
            throw new RuntimeException("CreditCard does not belong to client");
        }

        this.creditCardRepository.delete(creditCard);

        log.info("Successfully deleted CreditCard");
    }

    public CreditCardModel create(CreditCardModel model) {
        log.info("Attempting to create a credit card");

        String hashedCardNumber = this.passwordEncoder.encode(model.getCardNumber());
        model.setCardNumber(hashedCardNumber);

        System.out.println("Card: " + model);

        CreditCardModel savedCreditCard = this.creditCardRepository.save(model);

        log.info("Successfully created credit card");

        return savedCreditCard;
    }
}
