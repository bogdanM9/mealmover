package mealmover.backend.interfaces;

import mealmover.backend.models.ProductModel;

public interface ProductRatingDto {
    ProductModel getProduct();
    Double       getAverageRating();
    Long         getNumberOfReviews();
}