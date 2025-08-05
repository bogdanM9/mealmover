package mealmover.backend.mapper;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.dtos.responses.ProductResponseDto;
import mealmover.backend.interfaces.ProductRatingDto;
import mealmover.backend.models.ProductModel;
import mealmover.backend.models.ReviewModel;
import mealmover.backend.records.ProductData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = {
        SizeMapper.class,
        ReviewMapper.class,
        CategoryMapper.class,
        IngredientMapper.class,
        ExtraIngredientMapper.class
    }
)
public interface ProductMapper {
    ProductModel toModel(ProductCreateRequestDto dto);

    @Mapping(target = "category", ignore = true)
    ProductModel toModel(ProductData data);

    @Mapping(target = "sizes", source = "productSizes")
    @Mapping(target = "rating", expression = "java(calculateAverageRating(model))")
    @Mapping(target = "numberOfReviews", expression = "java(model.getReviews().size())")
    ProductResponseDto toDto(ProductModel model);

    default double calculateAverageRating(ProductModel model) {
        if (model.getReviews().isEmpty()) {
            return 0.0;
        }
        return model
            .getReviews()
            .stream()
            .mapToDouble(ReviewModel::getRating)
            .average()
            .orElse(0.0);
    }

    default ProductResponseDto ratingDtoToResponseDto(ProductRatingDto ratingDto) {
        ProductResponseDto dto = toDto(ratingDto.getProduct());

        double rating = ratingDto.getAverageRating() != null
            ? ratingDto.getAverageRating()
            : 0.0;

        dto.setRating(rating);
        dto.setNumberOfReviews(ratingDto.getNumberOfReviews().intValue());

        return dto;
    }

    List<ProductResponseDto> ratingDtosToResponseDtos(List<ProductRatingDto> ratingDtos);
}