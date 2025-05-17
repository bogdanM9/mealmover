package mealmover.backend.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class CreditCardResponseDto {
    private UUID id;
    private String cardNumber;
    private String holderName;
    private int expirationMonth;
    private int expirationYear;
}
