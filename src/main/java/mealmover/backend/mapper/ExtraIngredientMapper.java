package mealmover.backend.mapper;

import mealmover.backend.dtos.ExtraIngredientCreateDto;
import mealmover.backend.dtos.requests.ExtraIngredientCreateRequestDto;
import mealmover.backend.dtos.responses.ExtraIngredientResponseDto;
import mealmover.backend.models.ExtraIngredientModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExtraIngredientMapper {

    ExtraIngredientResponseDto toDto (ExtraIngredientModel model);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "ordersProductsExtraIngredients", ignore = true)
    ExtraIngredientModel toModel (ExtraIngredientCreateDto dto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "ordersProductsExtraIngredients", ignore = true)
    ExtraIngredientModel toModel (ExtraIngredientCreateRequestDto dto);
}
