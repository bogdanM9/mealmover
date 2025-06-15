package mealmover.backend.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class CategoryCreateRequestDto {
    @NotBlank(message="Name is required")
    @Size(message="Name must be between 2 and 30 characters", min = 2, max = 30)
    private String name;

    @NotBlank(message="Image URI is required")
    @Size(message="Image URI must be between 5 and 255 characters", min = 5, max = 255)
    private String imageUri;
}
