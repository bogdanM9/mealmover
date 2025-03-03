package mealmover.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(name="roles")
public class RoleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name="name", nullable=false,unique = true, length=50)
    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserModel> users = new HashSet<>();

    public RoleModel() {}

    public RoleModel(String name) {
        this.name = name;
    }
}