package mealmover.backend.dtos.requests;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Data
public class CreditCardCreateRequestDto {
    private String cardNumber;
    private String holderName;
    private int expirationMonth;
    private int expirationYear;
    private int securityCode;
    private boolean main;
}
