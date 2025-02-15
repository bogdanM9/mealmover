package mealmover.backend.repositories;

import mealmover.backend.models.AllergenModel;
import mealmover.backend.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AllergenRepository extends JpaRepository<AllergenModel, UUID> {
    boolean existsByName(String name);
    Optional<AllergenModel> findByName(String name);
}
