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
@Table(name="allergens")
public class AllergenModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @Column(name="name", nullable=false, length=100)
    private String name;

    @ManyToMany(mappedBy = "allergens")
    @ToString.Exclude
    private Set<IngredientModel> ingredients = new HashSet<>();
}