//package mealmover.backend.mapper;
//
//import mealmover.backend.dtos.requests.CreditCardCreateRequestDto;
//import mealmover.backend.dtos.responses.CreditCardResponseDto;
//import mealmover.backend.models.CreditCardModel;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//@Mapper(componentModel = "spring" )
//public interface CreditCardMapper {
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "client", ignore = true)
//    CreditCardModel toModel (CreditCardCreateRequestDto dto);
//
//    CreditCardResponseDto toDto (CreditCardModel model);
//
//}
