package mealmover.backend.messages;

import mealmover.backend.models.UserModel;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class UserMessages extends BaseMessages<UserModel> {
    public UserMessages(MessageSource messageSource) {
        super(UserModel.class, messageSource);
    }

    public String alreadyExistsByEmail(String email) {
        return this.alreadyExists("email");
    }

    public String notFoundByEmail() {
        return this.notFound("email");
    }

    public String invalidCredentials() {
        return messageSource.getMessage(
        "auth.login.invalid_credentials",
            new Object[]{},
            LocaleContextHolder.getLocale()
        );
    }
}