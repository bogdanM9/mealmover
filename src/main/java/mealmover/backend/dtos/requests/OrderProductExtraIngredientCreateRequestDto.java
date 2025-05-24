package mealmover.backend.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class OrderProductExtraIngredientCreateRequestDto {
    private int quantity;
    private UUID extraIngredientId;
}
