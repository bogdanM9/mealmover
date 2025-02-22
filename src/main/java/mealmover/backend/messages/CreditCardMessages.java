package mealmover.backend.messages;

import mealmover.backend.models.CreditCardModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class CreditCardMessages extends BaseMessages<CreditCardModel>{
    public CreditCardMessages(MessageSource messageSource) {
        super(CreditCardModel.class, messageSource);
    }

    public String alreadyExistsByNumber(String number) {
        return this.alreadyExists("number");
    }

}
