package mealmover.backend.messages;

import mealmover.backend.models.PendingClientModel;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PendingClientMessages extends BaseMessages<PendingClientModel> {
    public PendingClientMessages(MessageSource messageSource) {
        super(PendingClientModel.class, messageSource);
    }

    public String alreadyExistsByEmail() { return this.alreadyExists("email");
    }

    public String notfoundByEmail() { return this.notFound("email");
    }

    public String notfoundByToken() { return this.notFound("token");
    }

    public String activateFirst() {
        return messageSource.getMessage(
            "pending_client.activate_first",
            new Object[]{},
            LocaleContextHolder.getLocale()
        );
    }
}
