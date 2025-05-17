package mealmover.backend.mapper;

import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses={RoleMapper.class})
public interface UserMapper {
    @Mapping(source="model.roles", target="role", qualifiedByName = "toFirstDto")
    UserResponseDto toDto(UserModel model);
}
