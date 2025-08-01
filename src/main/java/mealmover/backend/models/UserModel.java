package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="first_name", nullable=false)
    private String firstName;

    @Column(name="last_name", nullable=false)
    private String lastName;

    @Column(nullable=false, unique=true)
    private String email;

    @Column
    private String password;

    @Column
    private String phoneNumber;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(nullable = false)
    private boolean passwordLoginEnabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @ToString.Exclude
    private Set<RoleModel> roles = new HashSet<>();

    public boolean hasPassword() {
        return this.password != null && !this.password.isBlank();
    }

    public void addRole(RoleModel role) {
        if (role != null) {
            if (this.roles == null) {
                this.roles = new HashSet<>();
            }
            this.roles.add(role);
        }
    }
}