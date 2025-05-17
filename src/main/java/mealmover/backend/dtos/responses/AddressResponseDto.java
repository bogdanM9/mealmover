package mealmover.backend.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddressResponseDto {
    private String id;
    private String county;
    private String street;
    private String details;
    private String postcode;
    private int number;
}
