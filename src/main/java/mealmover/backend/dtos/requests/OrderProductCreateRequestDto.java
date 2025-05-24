package mealmover.backend.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Data
public class OrderProductCreateRequestDto {
    private int quantity;
    private UUID productSizeId;
    private List<OrderProductExtraIngredientCreateRequestDto> extraIngredients;
}
