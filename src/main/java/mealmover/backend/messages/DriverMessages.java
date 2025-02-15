package mealmover.backend.messages;

import mealmover.backend.models.DriverModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class DriverMessages extends BaseMessages<DriverModel> {


    protected DriverMessages(MessageSource messageSource) {super(DriverModel.class, messageSource);}

    public String alreadyExistsByEmail() { return  this.alreadyExists("email");
    }

    public String notfoundByName() { return this.notFound("name");
    }
}

