package mealmover.backend.messages;

import mealmover.backend.models.ProductModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ProductMessages extends BaseMessages<ProductModel> {

    protected ProductMessages(MessageSource messageSource) {super(ProductModel.class, messageSource);}

    public String notfoundById() { return this.notFound("name");
    }
}
