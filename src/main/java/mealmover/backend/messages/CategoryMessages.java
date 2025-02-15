package mealmover.backend.messages;

import mealmover.backend.models.CategoryModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class CategoryMessages extends BaseMessages<CategoryModel> {

    public CategoryMessages(MessageSource messageSource) {
        super(CategoryModel.class, messageSource);
    }

    public String alreadyExistsByName(String name) {
        return this.alreadyExists("name");
    }
}
