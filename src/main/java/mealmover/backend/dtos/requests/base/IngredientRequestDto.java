package mealmover.backend.dtos.requests.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientRequestDto {
    @NotBlank(message="Name is required")
    @Size(message="Name must be between 2 and 200 characters", min = 2, max = 200)
    private String name;
}
