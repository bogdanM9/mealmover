package mealmover.backend.repositories;

import mealmover.backend.models.PendingClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PendingClientRepository extends JpaRepository<PendingClientModel, UUID> {
    Optional<PendingClientModel> findByEmail(String name);

    Optional<PendingClientModel> findByToken(String token);

    boolean existsByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM PendingClientModel p WHERE p.createdAt < :dateTime")
    int deleteByCreatedAtBefore(LocalDateTime dateTime);
}