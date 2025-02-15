package mealmover.backend.repositories;

import mealmover.backend.models.OrdersProductsExtraIngredientsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersProductsExtraIngredientsRepository extends
        JpaRepository<OrdersProductsExtraIngredientsModel, UUID> {
}
