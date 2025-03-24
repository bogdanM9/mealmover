package mealmover.backend.messages;


import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthMessages  {
    protected final MessageSource messageSource;

    public AuthMessages(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String registerSuccess() {
        return messageSource.getMessage(
        "auth.register.success",
            new Object[]{},
            LocaleContextHolder.getLocale()
        );
    }

    public String loginSuccess() {
        return messageSource.getMessage(
        "auth.login.success",
            new Object[]{},
            LocaleContextHolder.getLocale()
        );
    }

    public String activateSuccess() {
        return messageSource.getMessage(
        "auth.activate.success",
            new Object[]{},
            LocaleContextHolder.getLocale()
        );
    }
}
