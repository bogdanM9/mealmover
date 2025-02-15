package mealmover.backend.repositories;

import mealmover.backend.models.DriverModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DriverRepository extends JpaRepository<DriverModel, UUID> {
}
