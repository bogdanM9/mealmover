package mealmover.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "pending_clients")
public class PendingClientModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "first_name", nullable=false, length=500)
    private String firstName;

    @Column(name = "last_name", nullable=false, length=500)
    private String lastName;

    @Column(nullable=false, length=150)
    private String email;

    @Column(nullable=false, length=100)
    private String password;

    @Column(nullable=false, length=1000)
    private String token;
}