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
@Table(name="extra_ingredients")
public class ExtraIngredientModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @Column(nullable=false, length=200)
    private String name;

    @Column(nullable=false)
    private float price;

    @Column(nullable=false)
    private int weight;

    @ManyToMany(mappedBy = "extraIngredients")
    private Set<ProductModel> products = new HashSet<>();

    @OneToMany(mappedBy = "extraIngredient", fetch = FetchType.LAZY)
    private Set<OrdersProductsExtraIngredientsModel> ordersProductsExtraIngredients = new HashSet<>();
}
