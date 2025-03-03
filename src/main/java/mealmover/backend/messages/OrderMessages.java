package mealmover.backend.messages;

import mealmover.backend.models.OrderModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class OrderMessages extends BaseMessages<OrderModel>{


    protected OrderMessages( MessageSource messageSource) {
        super(OrderModel.class, messageSource);
    }

    public String alreadyExistsByEmail() {
        return "Order already exists with this email";
    }

    public String notfoundByName() {
        return "Order not found with this name";
    }
}
