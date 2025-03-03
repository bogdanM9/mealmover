package mealmover.backend.messages;

import mealmover.backend.models.StatusModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class StatusMessages extends BaseMessages<StatusModel>{
    public StatusMessages(MessageSource messageSource) {
        super(StatusModel.class, messageSource);
    }

    public String alreadyExistsByName() {
        return this.alreadyExists("name");
    }

    public String getStatusNotFound() {
        return this.notFound("status");
    }
}
