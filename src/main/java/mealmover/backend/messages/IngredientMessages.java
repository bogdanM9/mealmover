package mealmover.backend.messages;

import mealmover.backend.models.IngredientModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class IngredientMessages extends BaseMessages<IngredientModel>{
    public IngredientMessages(MessageSource messageSource) {
        super(IngredientModel.class, messageSource);
    }

    public String alreadyExistsByName() {
        return this.alreadyExists("name");
    }
}
