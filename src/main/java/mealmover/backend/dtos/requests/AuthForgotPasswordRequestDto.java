package mealmover.backend.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthForgotPasswordRequestDto {
    private String email;
}