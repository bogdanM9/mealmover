package mealmover.backend.messages;

import mealmover.backend.models.ClientModel;
import mealmover.backend.models.OperatorModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class OperatorMessages extends BaseMessages<OperatorModel> {

    public OperatorMessages(MessageSource messageSource) {
        super(OperatorModel.class, messageSource);
    }

    public String alreadyExistsByName() {
        return this.alreadyExists("name");
    }

    public String alreadyExistsByEmail() { return  this.alreadyExists("email");
    }

    public String notfoundByName() { return this.notFound("name");
    }
}
