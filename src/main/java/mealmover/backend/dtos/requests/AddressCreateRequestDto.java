package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressCreateRequestDto {

    private String county;
    private String city;
    private String details;
    private String postcode;
    private double lat;
    private double lng;

}
