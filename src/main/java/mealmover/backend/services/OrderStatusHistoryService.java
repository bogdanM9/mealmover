package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.models.OrderStatusHistoryModel;
import mealmover.backend.repositories.OrderStatusHistoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderStatusHistoryService {
    private final OrderStatusHistoryRepository orderStatusHistoryRepository;

    public void save(OrderStatusHistoryModel orderStatusHistoryModel) {
        this.orderStatusHistoryRepository.save(orderStatusHistoryModel);
    }

}
