package mealmover.backend.dtos.requests;

import lombok.Data;
import mealmover.backend.dtos.ExtraIngredientCreateDto;
import mealmover.backend.dtos.IngredientCreateDto;
import mealmover.backend.dtos.SizeCreateDto;

import java.util.List;
import java.util.UUID;

@Data
public class ProductCreateRequestDto  {
    private String name;
    private UUID categoryId;
    private List<SizeCreateDto> sizes;
    private List<IngredientCreateDto> ingredients;
    private List<ExtraIngredientCreateDto> extraIngredients;
}