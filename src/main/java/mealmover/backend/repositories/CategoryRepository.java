package mealmover.backend.repositories;

import mealmover.backend.interfaces.CategoryRatingDto;
import mealmover.backend.models.CategoryModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {
    boolean existsByName(String name);
    Optional<CategoryModel> findByName(String name);
    @Query("""
        SELECT  c            AS category,
            AVG(r.rating)    AS averageRating,
            COUNT(r)         AS numberOfReviews
        FROM    CategoryModel c
        LEFT JOIN c.products p     
        LEFT JOIN p.reviews  r          
        GROUP BY c
        ORDER BY averageRating   DESC NULLS LAST,
                 numberOfReviews DESC
    """)
    List<CategoryRatingDto> findTopRatedCategories(Pageable pageable);
}