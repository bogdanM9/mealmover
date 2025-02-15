package mealmover.backend.messages;

import mealmover.backend.models.SizeModel;
import mealmover.backend.models.UserModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class SizeMessages extends BaseMessages<SizeModel> {
    public SizeMessages(MessageSource messageSource) {
        super(SizeModel.class, messageSource);
    }

    public String alreadyExistsByName(String name) {
        return this.alreadyExists("name");
    }
}
