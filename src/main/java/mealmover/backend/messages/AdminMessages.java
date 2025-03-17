package mealmover.backend.messages;

import mealmover.backend.models.AddressModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class AdminMessages extends BaseMessages<AddressModel>{

    public AdminMessages(MessageSource messageSource) {
        super(AddressModel.class, messageSource);
    }

        public String alreadyExistsByStreet(String street) {
            return this.alreadyExists("street");
        }

        public String alreadyExistsByCity(String city) {
            return this.alreadyExists("city");
        }

        public String alreadyExistsByState(String state) {
            return this.alreadyExists("state");
        }

        public String alreadyExistsByZip(String zip) {
            return this.alreadyExists("zip");
        }

        public String alreadyExistsByCountry(String country) {
            return this.alreadyExists("country");
        }

}
