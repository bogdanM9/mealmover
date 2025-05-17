//package mealmover.backend.mapper;
//
//import mealmover.backend.dtos.IngredientCreateDto;
//import mealmover.backend.dtos.requests.IngredientCreateRequestDto;
//import mealmover.backend.dtos.responses.IngredientResponseDto;
//import mealmover.backend.models.IngredientModel;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring", uses = {AllergenMapper.class})
//public interface IngredientMapper {
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "products", ignore = true)
//    IngredientModel toModel(IngredientCreateDto dto);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "products", ignore = true)
//    @Mapping(target = "allergens", ignore = true)
//    IngredientModel toModel(IngredientCreateRequestDto dto);
//
//    IngredientResponseDto toDto(IngredientModel model);
//}
