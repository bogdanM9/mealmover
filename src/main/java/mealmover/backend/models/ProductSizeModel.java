package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import mealmover.backend.services.ProductService;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name = "product_sizes")
public class ProductSizeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="size_id", nullable=false)
    private SizeModel size;

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    @ToString.Exclude
    private ProductModel product;

    public ProductSizeModel(SizeModel size, ProductModel product) {
        this.size = size;
        this.product = product;
    }

    public  ProductSizeModel(){

    }
}
