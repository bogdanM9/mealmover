package mealmover.backend.configurations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.RoleCreateRequestDto;
import mealmover.backend.enums.Role;
import mealmover.backend.services.RoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {
    private final RoleService roleService;

    @Override
    public void run(String... args) {
        log.info("Seeding data...");
        seedRoles();
        log.info("Data seeding completed.");
    }

    private void seedRoles() {
        log.info("Seeding roles...");

        RoleCreateRequestDto requestDtoClient = RoleCreateRequestDto
            .builder()
            .name(Role.CLIENT.toCapitalize())
            .build();

        if(!this.roleService.existsByName(Role.CLIENT.toCapitalize())) {
            this.roleService.create(requestDtoClient);
        }

        log.info("Roles seeding completed.");
    }
}