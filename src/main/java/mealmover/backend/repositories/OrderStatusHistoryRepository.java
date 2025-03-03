package mealmover.backend.repositories;

import mealmover.backend.models.OrderStatusHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistoryModel, UUID> {
}
