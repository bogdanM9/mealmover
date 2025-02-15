package mealmover.backend.repositories;

import mealmover.backend.models.OperatorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OperatorRepository extends JpaRepository<OperatorModel, UUID> {
}
