package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.CategoryCreateRequestDto;
import mealmover.backend.dtos.responses.CategoryResponseDto;
import mealmover.backend.interfaces.CategoryRatingDto;
import mealmover.backend.models.CategoryModel;
import mealmover.backend.records.CategoryData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    CategoryModel toModel(CategoryData data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "imageUri", ignore = true)
    CategoryModel toModel(CategoryCreateRequestDto dto);

    CategoryResponseDto toDto (CategoryModel model);

    default CategoryResponseDto ratingDtoToResponseDto(CategoryRatingDto ratingDto) {
        CategoryResponseDto dto = toDto(ratingDto.getCategory());

        double rating = ratingDto.getAverageRating() != null
            ? ratingDto.getAverageRating()
            : 0.0;

        dto.setRating(rating);
        dto.setNumberOfReviews(ratingDto.getNumberOfReviews().intValue());

        return dto;
    }

    List<CategoryResponseDto> ratingDtosToResponseDtos(List<CategoryRatingDto> topCategories);
}