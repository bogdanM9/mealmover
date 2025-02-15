package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.UserCreateRequestDto;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.models.UserModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toDto (UserModel model);
//    UserModel toModel (UserCreateRequestDto dto);
}
