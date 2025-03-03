package mealmover.backend.repositories;

import mealmover.backend.models.StatusModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<StatusModel, UUID> {
    Optional<StatusModel> findByName(String name);
}
