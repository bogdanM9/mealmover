package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserChangeEmail {
    private String newEmail;
    private String password;
}
