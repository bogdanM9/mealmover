package mealmover.backend.security;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.responses.UserResponseDto;
import mealmover.backend.enums.Role;
import mealmover.backend.mapper.ClientMapper;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.UserModel;
import mealmover.backend.services.ClientDataService;
import mealmover.backend.services.ClientService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final ClientMapper clientMapper;
    private final SecurityUtils securityUtils;
    private final ClientDataService clientDataService;

    public Role getCurrentUserFirstRole() {
        String firstAuthority = this.securityUtils.getFirstAuthority();
        return Role.fromCapitalize(firstAuthority);
    }

    @Transactional(readOnly = true)
    public UserModel getModelCurrentUser() {
        UUID id = this.securityUtils.getCurrentUser().getId();

        Role role = this.getCurrentUserFirstRole();

        return switch (role) {
            case CLIENT -> this.clientDataService.getById(id);
        };
    }

    @Transactional(readOnly = true)
    public UserResponseDto getDtoCurrentUser() {
        UserModel userModel = this.getModelCurrentUser();

        Role role = this.getCurrentUserFirstRole();

        return switch (role) {
            case CLIENT -> this.clientMapper.toDto((ClientModel) userModel);
        };
    }
}