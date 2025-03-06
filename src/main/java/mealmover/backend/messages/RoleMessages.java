package mealmover.backend.messages;

import mealmover.backend.models.RoleModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class RoleMessages extends BaseMessages<RoleModel> {
    public RoleMessages(MessageSource messageSource) {
        super(RoleModel.class, messageSource);
    }

    public String alreadyExistsByName() {
        return this.alreadyExists("name");
    }


    public String notFoundByEmail() {
        return this.notFoundByEmail();
    }
}