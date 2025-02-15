package mealmover.backend.dtos.requests;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import mealmover.backend.dtos.requests.base.UserRequestDto;

@Getter
@Setter
public class DriverCreateRequestDto extends UserRequestDto {
    @Email(message = "Email format is not ok!")
    private String email;

    private String password;
}
