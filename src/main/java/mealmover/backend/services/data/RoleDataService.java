package mealmover.backend.services.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.RoleConstants;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.models.RoleModel;
import mealmover.backend.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleDataService {
    private final RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public RoleModel getByName(String name) {
        log.info("Getting user with name {}", name);
        return this.roleRepository
            .findByName(name)
            .orElseThrow(() -> new NotFoundException(RoleConstants.NOT_FOUND_BY_NAME));
    }
}
