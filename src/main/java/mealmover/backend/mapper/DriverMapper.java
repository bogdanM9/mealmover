package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.DriverCreateRequestDto;
import mealmover.backend.dtos.responses.DriverResponseDto;
import mealmover.backend.models.DriverModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverResponseDto toDto (DriverModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    DriverModel toModel (DriverCreateRequestDto dto);
}
