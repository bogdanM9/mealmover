package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderCreateRequestDto {
    private UUID statusId;
    private UUID clientId;
    private UUID addressId;
    private String details;
    private List<OrderProductCreateRequestDto> products;
    private AddressCreateRequestDto address;
}
