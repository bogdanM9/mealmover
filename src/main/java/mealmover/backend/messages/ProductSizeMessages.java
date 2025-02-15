package mealmover.backend.messages;

import mealmover.backend.models.ProductSizeModel;
import org.springframework.context.MessageSource;

public class ProductSizeMessages extends BaseMessages<ProductSizeModel> {
    public ProductSizeMessages(MessageSource messageSource) {
        super(ProductSizeModel.class, messageSource);
    }

}
