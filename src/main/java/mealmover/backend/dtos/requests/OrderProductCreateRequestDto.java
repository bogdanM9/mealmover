package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class OrderProductCreateRequestDto {
    private int quantity;
    private UUID productSizeId;
    private List<OrderProductExtraIngredientCreateRequestDto> extraIngredients;
}
