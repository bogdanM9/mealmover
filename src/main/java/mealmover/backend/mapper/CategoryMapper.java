package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.CategoryCreateRequestDto;
import mealmover.backend.dtos.responses.CategoryResponseDto;
import mealmover.backend.models.CategoryModel;
import mealmover.backend.records.CategoryData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

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
}
