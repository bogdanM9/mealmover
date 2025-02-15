package mealmover.backend.repositories;

import mealmover.backend.models.ExtraIngredientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExtraIngredientRepository extends JpaRepository<ExtraIngredientModel, UUID> {
    boolean existsByName(String name);
    Optional<ExtraIngredientModel> findByName(String name);
}
