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
    @OneToMany(mappedBy="client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AddressModel> addresses = new HashSet<>();

    @OneToMany(mappedBy="client", fetch= FetchType.LAZY)
    private Set<CreditCardModel> creditCards = new HashSet<>();

    @OneToMany(mappedBy="client", fetch= FetchType.LAZY)
    private Set<ReviewModel> reviews = new HashSet<>();

    @OneToMany(mappedBy="client", fetch= FetchType.LAZY)
    private Set<OrderModel> orders = new HashSet<>();
}