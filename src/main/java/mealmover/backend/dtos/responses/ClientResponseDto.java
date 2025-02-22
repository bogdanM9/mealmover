package mealmover.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;
import mealmover.backend.models.AddressModel;

import java.util.Set;
import java.util.UUID;
@Getter
@Setter
public class ClientResponseDto extends UserResponseDto {
    private Set<AddressResponseDto> addresses;
    private Set<CreditCardResponseDto> creditCards;
}
