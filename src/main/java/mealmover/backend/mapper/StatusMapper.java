package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.StatusCreateRequestDto;
import mealmover.backend.dtos.responses.StatusResponseDto;
import mealmover.backend.models.StatusModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatusMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    StatusModel toModel (StatusCreateRequestDto dto);

    StatusResponseDto toDto (StatusModel model);


}
