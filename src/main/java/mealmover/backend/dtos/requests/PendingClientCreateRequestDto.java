package mealmover.backend.dtos.requests;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PendingClientCreateRequestDto {
    private String firstName;
    private String lastName;

    @Email(message = "Email format is not ok!")
    private String email;

    private String password;

}
