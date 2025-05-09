package mealmover.backend.repositories;

import mealmover.backend.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, UUID> {
    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    Optional<ClientModel> findByEmail(String email);

    ClientModel findByPhoneNumber(String phoneNumber);
}
