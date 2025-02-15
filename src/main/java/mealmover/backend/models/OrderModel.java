package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="orders")
public class OrderModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(nullable=false)
    private float total;

    @Column(nullable=false, length=1000)
    private String details;

    @Column(name = "delivery_time", nullable=false)
    private LocalDateTime deliveryTime;

    @ManyToOne
    @JoinColumn(name="status_id", nullable=false)
    private StatusModel status;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private ClientModel client;

    @ManyToOne
    @JoinColumn(name="address_id", nullable=false)
    private AddressModel address;

    @OneToMany(mappedBy="order", fetch= FetchType.LAZY)
    private Set<OrderProductModel> orderProducts = new HashSet<>();
    @Column(name="created_at" ,nullable=false)
    private LocalDateTime createdAt;

    @Column(name="updated_at" ,nullable=false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
