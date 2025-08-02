package mealmover.backend.mapper;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.dtos.responses.ProductResponseDto;
import mealmover.backend.interfaces.ProductRatingDto;
import mealmover.backend.models.ProductModel;
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
    ProductResponseDto toDto(ProductModel model);

    default ProductResponseDto ratingDtoToResponseDto(ProductRatingDto ratingDto) {
        ProductResponseDto dto = toDto(ratingDto.getProduct());

        float rating = ratingDto.getAverageRating() != null
            ? ratingDto.getAverageRating().floatValue()
            : 0f;

        dto.setRating(rating);

        dto.setNrReviews(ratingDto.getReviewCount().intValue());

        return dto;
    }

    List<ProductResponseDto> ratingDtosToResponseDtos(List<ProductRatingDto> ratingDtos);
}