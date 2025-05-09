package mealmover.backend.repositories;

import mealmover.backend.models.ProductSizeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductSizeRepository extends JpaRepository<ProductSizeModel, UUID> {
}