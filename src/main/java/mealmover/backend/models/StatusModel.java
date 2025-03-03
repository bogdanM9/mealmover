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
@Table(name="statuses")
public class StatusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy="status", fetch= FetchType.LAZY)
    private Set<OrderModel> orders = new HashSet<>();

    public StatusModel(String name) {
        this.name = name;
    }

    public StatusModel() {
    }
}