package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthForgotPasswordRequestDto {
    private String email;
}
