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
@Table(name="ingredients")
public class IngredientModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @Column(name="name", nullable=false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "ingredients", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<ProductModel> products = new HashSet<>();

    @ManyToMany()
    @JoinTable(
        name = "ingredients_allergens",
        joinColumns = @JoinColumn(name = "ingredient_id"),
        inverseJoinColumns = @JoinColumn(name = "allergen_id")
    )
    private Set<AllergenModel> allergens = new HashSet<>();
}
