package mealmover.backend.repositories;

import mealmover.backend.models.ReviewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewModel, UUID> {
    List<ReviewModel> findByProductId(UUID productId);

    boolean existsByClientIdAndProductId(UUID clientId, UUID productId);
}