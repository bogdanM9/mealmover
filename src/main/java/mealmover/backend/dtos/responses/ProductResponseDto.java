package mealmover.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ProductResponseDto {
    private UUID id;
    private String name;
    private float rating;
    private int nrReviews;
    private String imageUri;
    private CategoryResponseDto category;
    private Set<SizeResponseDto> sizes;
    private Set<IngredientResponseDto> ingredients;
    private Set<ExtraIngredientResponseDto> extraIngredients;
}