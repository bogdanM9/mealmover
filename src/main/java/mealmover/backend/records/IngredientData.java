package mealmover.backend.records;

import java.util.List;

public record IngredientData(
    String name,
    List<AllergenData> allergens) {
}
