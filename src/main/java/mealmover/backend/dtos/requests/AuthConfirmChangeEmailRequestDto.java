package mealmover.backend.dtos.requests;

import lombok.Data;

@Data
public class AuthConfirmChangeEmailRequestDto {
    private String token;
}