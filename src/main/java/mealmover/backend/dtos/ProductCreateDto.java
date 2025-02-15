package mealmover.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class ProductCreateDto {
    private String name;
    private UUID categoryId;
    private List<SizeCreateDto> sizes;
    private List<IngredientCreateDto> ingredients;
    private List<ExtraIngredientCreateDto> extraIngredients;
}