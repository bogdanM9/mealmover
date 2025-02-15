package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.CreatePendingClientRequestDto;
import mealmover.backend.dtos.responses.PendingClientResponseDto;
import mealmover.backend.models.PendingClientModel;
import org.mapstruct.Mapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

@Mapper(componentModel = "spring")
public interface PendingClientMapper {
    PendingClientResponseDto toDto(PendingClientModel model);

    PendingClientModel toModel(CreatePendingClientRequestDto dto);

    default UserDetails toUserDetails(PendingClientModel model) {
        if (model == null) {
            return null;
        }

        return User
            .withUsername(model.getEmail())
            .password(model.getPassword())
            .authorities(Collections.emptyList())
            .build();
    }
}
