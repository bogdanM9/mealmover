package mealmover.backend.messages;

import mealmover.backend.models.UserModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class UserMessages extends BaseMessages<UserModel> {
    public UserMessages(MessageSource messageSource) {
        super(UserModel.class, messageSource);
    }

    public String alreadyExistsByEmail(String email) {
        return this.alreadyExists("email");
    }
}
