package mealmover.backend.interfaces;

import mealmover.backend.models.CategoryModel;

public interface CategoryRatingDto {
    CategoryModel getCategory();
    Double        getAverageRating();
    Long          getNumberOfReviews();
}