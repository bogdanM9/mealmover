package mealmover.backend.repositories;

import mealmover.backend.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    boolean existsByName(String name);
    Optional<RoleModel> findByName(String name);
}