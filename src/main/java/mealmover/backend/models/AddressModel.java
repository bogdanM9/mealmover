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
@Table(name = "addresses")
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String county;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(nullable = false, length = 500)
    private String details;

    @Column(nullable = false, length = 50)
    private String postcode;

    @Column(name = "lat", nullable = false)
    private double lat;

    @Column(name = "lng", nullable = false)
    private double lng;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientModel client;

    @OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderModel> orders = new HashSet<>();
}
