package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.RoleCreateRequestDto;
import mealmover.backend.dtos.responses.RoleResponseDto;
import mealmover.backend.models.RoleModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDto toDto (RoleModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    RoleModel toModel (RoleCreateRequestDto dto);

    @Named("toFirstDto")
    default RoleResponseDto toFirstDto(Set<RoleModel> roles) {
        if(roles == null || roles.isEmpty()) {
            return null;
        }
        return toDto(roles.iterator().next());
    }
}