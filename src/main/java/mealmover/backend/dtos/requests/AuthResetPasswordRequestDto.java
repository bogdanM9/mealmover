package mealmover.backend.dtos.requests;

import lombok.Data;

@Data
public class AuthResetPasswordRequestDto {
    private String token;
    private String newPassword;
}