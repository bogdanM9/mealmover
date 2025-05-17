//package mealmover.backend.mapper;
//
//import mealmover.backend.dtos.requests.CreateCategoryRequestDto;
//import mealmover.backend.dtos.responses.CategoryResponseDto;
//import mealmover.backend.models.CategoryModel;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface CategoryMapper {
//
//    CategoryResponseDto toDto (CategoryModel model);
//
//    @Mapping(target = "id", ignore = true)
//    CategoryModel toModel (CreateCategoryRequestDto dto);
//}
