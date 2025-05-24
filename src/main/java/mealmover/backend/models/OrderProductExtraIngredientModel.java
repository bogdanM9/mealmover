package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="orders_products_extra_ingredients")
public class OrderProductExtraIngredientModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(nullable=false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "order_product_id", nullable = false)
    private OrderProductModel orderProduct;

    @ManyToOne
    @JoinColumn(name = "extra_ingredient_id", nullable = false)
    private ExtraIngredientModel extraIngredient;

    public OrderProductExtraIngredientModel(int quantity, ExtraIngredientModel modelById) {
        this.quantity = quantity;
        this.extraIngredient = modelById;
    }
    public OrderProductExtraIngredientModel() {
    }
}
