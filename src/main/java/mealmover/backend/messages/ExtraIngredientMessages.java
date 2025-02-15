package mealmover.backend.messages;

import mealmover.backend.models.ExtraIngredientModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ExtraIngredientMessages extends BaseMessages<ExtraIngredientModel>{
    public ExtraIngredientMessages(MessageSource messageSource) {
        super(ExtraIngredientModel.class, messageSource);
    }

    public String alreadyExistsByName() {
        return this.alreadyExists("name");
    }
}
