package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "order_status_history")
public class OrderStatusHistoryModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    private StatusModel status;

    @Column(name = "changed_id", nullable = false)
    private java.time.LocalDateTime changedAt;

    @PrePersist
    protected void onCreate() {
        this.changedAt = java.time.LocalDateTime.now();
    }
}
