package mealmover.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class IngredientCreateDto {
    private String name;
    private List<AllergenCreateDto> allergens;
}
