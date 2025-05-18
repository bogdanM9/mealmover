package mealmover.backend.dtos.requests;

import lombok.Data;

@Data
public class AddressCreateRequestDto {
    private String county;
    private String city;
    private String street;
    private Integer number;
    private String postcode;
    private String details;
    private boolean main;
}