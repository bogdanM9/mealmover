package mealmover.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderResponseDto {

    private UUID id;
    private StatusResponseDto status;
    private ClientResponseDto client;
    private AddressResponseDto address;
    private String details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<ProductResponseDto> products;

}
