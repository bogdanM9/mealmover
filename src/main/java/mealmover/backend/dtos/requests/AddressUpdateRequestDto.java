package mealmover.backend.dtos.requests;

import lombok.Data;

@Data
public class AddressUpdateRequestDto {
    private String county;
    private String city;
    private String street;
    private Integer number;
    private String postcode;
    private String details;
}