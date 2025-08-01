package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name="clients")
public class ClientModel extends UserModel{
    @Column(name = "oauth2_id")
    private String oAuth2Id; // Provider's unique user I

    @Column(name = "oauth2_provider")
    private String oAuth2Provider; // "google", "facebook", etc.

    @OneToMany(mappedBy="client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderModel> orders = new HashSet<>();

    @OneToMany(mappedBy="client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ReviewModel> reviews = new HashSet<>();

    @OneToMany(mappedBy="client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AddressModel> addresses = new HashSet<>();

    @OneToMany(mappedBy="client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CreditCardModel> creditCards = new HashSet<>();
}