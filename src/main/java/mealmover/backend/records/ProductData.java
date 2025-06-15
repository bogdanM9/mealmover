package mealmover.backend.records;

import java.util.List;

public record ProductData(
    String name,
    String imageUri,
    String category,
    List<SizeData> sizes,
    List<IngredientData> ingredients,
    List<ExtraIngredientData> extraIngredients
) {}