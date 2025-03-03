package mealmover.backend.repositories;

import mealmover.backend.models.OrderProductExtraIngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersProductsExtraIngredientsRepository extends
        JpaRepository<OrderProductExtraIngredientModel, UUID> {
}
