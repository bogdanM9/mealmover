package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class ReviewModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable=false, length=500)
    private String title;

    @Column(nullable=false)
    private float rating;

    @Column(nullable=false, length=2000)
    private String text;

    @Column(name="created_at" ,nullable=false)
    private LocalDateTime postedAt;

    @Column(name="updated_at" ,nullable=false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.postedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name="product_id", nullable=false)
    private ProductModel product;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private ClientModel client;
}