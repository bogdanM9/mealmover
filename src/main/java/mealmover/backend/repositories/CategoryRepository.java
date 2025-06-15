package mealmover.backend.repositories;

import mealmover.backend.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    boolean existsByName(String name);

//    CategoryModel findByName(String name);


    Optional<CategoryModel> findByName(String name);
}
