package mealmover.backend.dtos.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserChangeEmail {
    private String newEmail;
    private String password;
}
