package mealmover.backend.mapper;

import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses={RoleMapper.class},
    unmappedTargetPolicy=ReportingPolicy.WARN
)
public interface UserMapper {
    @Mapping(source="model.roles", target="role", qualifiedByName = "toFirstDto")
    UserResponseDto toDto (UserModel model);

    default UserDetails toUserDetails(UserModel model) {
        if(model == null) {
            return null;
        }

        List<SimpleGrantedAuthority> authorities = model
            .getRoles()
            .stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
            .toList();

        return User
            .withUsername(model.getEmail())
            .password(model.getPassword())
            .authorities(authorities)
            .build();
    }
}
