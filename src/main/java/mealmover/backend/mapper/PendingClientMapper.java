package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.AuthRegisterRequestDto;
import mealmover.backend.dtos.responses.PendingClientResponseDto;
import mealmover.backend.models.PendingClientModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PendingClientMapper {
    PendingClientResponseDto toDto(PendingClientModel model);
    PendingClientModel toModel(AuthRegisterRequestDto dto);
}