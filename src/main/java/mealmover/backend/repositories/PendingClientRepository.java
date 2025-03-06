package mealmover.backend.repositories;

import mealmover.backend.models.PendingClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingClientRepository extends JpaRepository<PendingClientModel, UUID> {
    Optional<PendingClientRepository> findByEmail(String name);

    Optional<PendingClientModel> findByToken(String token);
}
