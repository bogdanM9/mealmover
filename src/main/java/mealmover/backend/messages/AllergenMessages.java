package mealmover.backend.messages;

import mealmover.backend.models.AllergenModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AllergenMessages extends BaseMessages<AllergenModel>  {
    public AllergenMessages(MessageSource messageSource) {
        super(AllergenModel.class, messageSource);
    }

    public String alreadyExistsByName(String name) {
        return this.alreadyExists("name");
    }
}
