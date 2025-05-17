package mealmover.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import mealmover.backend.models.AddressModel;

import java.util.Set;
import java.util.UUID;
@Data
@JsonTypeName("client")
@EqualsAndHashCode(callSuper = true)
public class ClientResponseDto extends UserResponseDto {
    private Set<AddressResponseDto> addresses;
    private Set<CreditCardResponseDto> creditCards;
}
