package mealmover.backend.dtos.requests;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class AuthRegisterClientRequestDto  {
    // vreau sa pui mai multe validari, adica pe @NotBlank, @Size(min 2 max 60)
    private String firstName;

    // @NotBlank, @Size(min 2 max 30)
    private String lastName;

    // NotBlank
    @Email(message = "Email format is not ok!")
    private String email;

    // NotBlank
    // Size(min 8 max 30)
    private String password;

    private String phoneNumber;
}