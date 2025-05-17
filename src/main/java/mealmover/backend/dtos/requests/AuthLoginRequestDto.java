package mealmover.backend.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthLoginRequestDto {
    private String email;
    private String password;
}