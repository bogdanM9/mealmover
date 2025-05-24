package mealmover.backend.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AddressResponseDto {
    private String id;
    private String county;
    private String street;
    private int number;
    private String city;
    private String details;
    private String postcode;
    private boolean main = false;
}
