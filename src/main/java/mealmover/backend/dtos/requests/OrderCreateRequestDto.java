package mealmover.backend.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
public class OrderCreateRequestDto {
    private UUID addressId;
    private String details;
    private List<OrderProductCreateRequestDto> products;
}
