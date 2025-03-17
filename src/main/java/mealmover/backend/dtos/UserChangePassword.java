package mealmover.backend.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangePassword {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
