package mealmover.backend.messages;

import mealmover.backend.models.ReviewModel;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ReviewMessages extends BaseMessages<ReviewModel>{
    public ReviewMessages(MessageSource messageSource) {
        super(ReviewModel.class, messageSource);
    }

    public String alreadyExistsByRating(String rating) {
        return this.alreadyExists("rating");
    }

    public String alreadyExistsByComment(String comment) {
        return this.alreadyExists("comment");
    }

    public String alreadyExistsByRestaurantId(String restaurantId) {
        return this.alreadyExists("restaurantId");
    }

    public String alreadyExistsByUserId(String userId) {
        return this.alreadyExists("userId");
    }

}
