package mealmover.backend.dtos.requests;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserCreateRequestDto {
    @Email(message = "Email format is not ok!")
    private String email;
    private String password;
    private String role;
}
