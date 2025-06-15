package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name="extra_ingredients")
public class ExtraIngredientModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable=false, length=200)
    private String name;

    @Column(nullable=false)
    private float price;

    @Column(nullable=false)
    private int weight;

    @ManyToMany(mappedBy = "extraIngredients")
    @ToString.Exclude
    private Set<ProductModel> products = new HashSet<>();

    @OneToMany(mappedBy = "extraIngredient", fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<OrderProductExtraIngredientModel> ordersProductsExtraIngredients = new HashSet<>();

    public ExtraIngredientModel() {
        // Default constructor
    }

    public ExtraIngredientModel(String name, float price, int weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
    }
}
