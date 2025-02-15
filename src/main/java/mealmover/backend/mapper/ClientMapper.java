package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.ClientCreateRequestDto;
import mealmover.backend.dtos.responses.ClientResponseDto;
import mealmover.backend.models.ClientModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientResponseDto toDto (ClientModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "creditCards", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "orders", ignore = true)
    ClientModel toModel (ClientCreateRequestDto dto);
}