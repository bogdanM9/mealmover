package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.OperatorCreateRequestDto;
import mealmover.backend.dtos.responses.OperatorResponseDto;
import mealmover.backend.models.OperatorModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperatorMapper {

    OperatorResponseDto toDto (OperatorModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    OperatorModel toModel (OperatorCreateRequestDto dto);
}
