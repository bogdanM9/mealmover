package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="orders_products")
public class OrderProductModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private OrderModel order;

    @ManyToOne
    @JoinColumn(name = "product_size_id", nullable = false)
    private ProductSizeModel productSize;

    @OneToMany(mappedBy = "orderProduct", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrdersProductsExtraIngredientsModel> extraIngredients = new HashSet<>();
}
