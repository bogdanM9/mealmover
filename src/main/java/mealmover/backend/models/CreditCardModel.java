package mealmover.backend.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="credit_cards")
public class CreditCardModel {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID id;

    @Column(name="card_number", nullable=false, length=19)
    private String cardNumber;

    @Column(name="holder_name", nullable=false, length=50)
    private String holderName;

    @Column(name="expiration_month", nullable=false)
    private int expirationMonth;

    @Column(name="expiration_year", nullable=false)
    private int expirationYear;

    @Column(name="security_code", nullable=false)
    private int securityCode;

    @Column(name="is_main", nullable=false)
    private boolean isMain;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private ClientModel client;
}