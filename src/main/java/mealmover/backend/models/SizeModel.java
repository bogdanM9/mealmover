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
@Table(name = "sizes")
public class SizeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private int weight;

    @OneToMany(mappedBy="size", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<ProductSizeModel> productSizes = new HashSet<>();

    public SizeModel(String name, int weight, float price) {
        this.name = name;
        this.weight = weight;
        this.price = price;
    }
    public SizeModel() {
        // Default constructor for JPA
    }
}
