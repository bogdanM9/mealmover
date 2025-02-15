package mealmover.backend.repositories;

import mealmover.backend.models.SizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SizeRepository extends JpaRepository<SizeModel, UUID> {
    Optional<SizeModel> findByName(String name);
    boolean existsByName(String name);
}