package mealmover.backend.repositories;

import mealmover.backend.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByFirstName(String name);
    Optional<UserModel> findByEmail(String name);
    boolean existsByEmail(String email);
}
