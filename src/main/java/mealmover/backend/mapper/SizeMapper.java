//package mealmover.backend.mapper;
//
//import mealmover.backend.dtos.SizeCreateDto;
//import mealmover.backend.dtos.requests.CreateSizeRequestDto;
//import mealmover.backend.dtos.responses.SizeResponseDto;
//import mealmover.backend.models.ProductSizeModel;
//import mealmover.backend.models.SizeModel;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring")
//public interface SizeMapper {
//    SizeResponseDto toDto(SizeModel model);
//    @Mapping(target = "id", source = "size.id")
//    @Mapping(target = "name", source = "size.name")
//    @Mapping(target = "price", source = "size.price")
//    @Mapping(target = "weight", source = "size.weight")
//    SizeResponseDto toDto(ProductSizeModel model);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "productSizes", ignore = true)
//    SizeModel toModel(SizeCreateDto dto);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "productSizes", ignore = true)
//    SizeModel toModel(CreateSizeRequestDto dto);
//}