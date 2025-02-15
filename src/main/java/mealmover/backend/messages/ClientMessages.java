package mealmover.backend.messages;

import mealmover.backend.models.ClientModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ClientMessages extends BaseMessages<ClientModel>{
    public ClientMessages(MessageSource messageSource) {
        super(ClientModel.class, messageSource);
    }

    public String alreadyExistsByName() {
        return this.alreadyExists("name");
    }

    public String alreadyExistsByEmail() { return  this.alreadyExists("email");
    }

    public String notfoundByName() { return this.notFound("name");
    }
}
