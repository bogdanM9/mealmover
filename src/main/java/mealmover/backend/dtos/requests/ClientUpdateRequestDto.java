package mealmover.backend.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientUpdateRequestDto{
    @NotBlank(message = "Name is required")
    @Size(message = "Name must be between 2 and 30 characters", min = 2, max = 30)
    private String firstName;

    private String lastName;

    private String phoneNumber;
}
