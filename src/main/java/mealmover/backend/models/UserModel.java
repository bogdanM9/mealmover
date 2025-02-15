package mealmover.backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="first_name", nullable=false, length=100)
    private String firstName;

    @Column(name="last_name", nullable=false, length=50)
    private String lastName;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    private RoleModel role;
}