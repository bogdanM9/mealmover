package mealmover.backend.dtos.requests.base;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "First name is required")
    @Size(message = "First name must be between 2 and 60 characters", min = 2, max = 60)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(message = "Last name must be between 2 and 60 characters", min = 2, max = 30)
    private String lastName;

    private String phoneNumber;
}
