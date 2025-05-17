package mealmover.backend.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleCreateRequestDto {
    @NotBlank(message="Name is required")
    @Size(message="Name must be between 2 and 30 characters", min = 2, max = 30)
    private String name;
}