package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.AddressCreateRequestDto;
import mealmover.backend.dtos.responses.AddressResponseDto;
import mealmover.backend.models.AddressModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "id", ignore = true)
    AddressModel toModel (AddressCreateRequestDto dto);

    AddressResponseDto toDto (AddressModel model);

}
