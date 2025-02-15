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
@Table(name="categories")
public class CategoryModel {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;

    @Column(name="name", nullable=false, length=200)
    private String name;

    @OneToMany(mappedBy="category", fetch= FetchType.LAZY)
    private Set<ProductModel> products = new HashSet<>();
}
