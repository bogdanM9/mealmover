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
@Table(name = "products")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private float rating;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(name = "image_uri", nullable = false)
    private String imageUri;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryModel category;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReviewModel> reviews = new HashSet<>();

    @OneToMany(
        mappedBy = "product",
        fetch = FetchType.LAZY,
        orphanRemoval = true,
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    private Set<ProductSizeModel> productSizes = new HashSet<>();

    @ManyToMany(
        cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
        }
    )
    @JoinTable(
        name = "products_ingredients",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<IngredientModel> ingredients = new HashSet<>();

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(
            name = "products_extra_ingredients",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "extra_ingredient_id")
    )
    private Set<ExtraIngredientModel> extraIngredients = new HashSet<>();
}
