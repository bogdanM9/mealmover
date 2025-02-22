package mealmover.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponseDto {
    private String id;
    private String county;
    private String details;
    private String postcode;
    private double lat;
    private double lng;
}
