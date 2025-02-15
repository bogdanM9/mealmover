package mealmover.backend.repositories;

import mealmover.backend.models.IngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientModel, UUID> {
    Optional<IngredientModel> findByName(String name);
    boolean existsByName(String name);
}
