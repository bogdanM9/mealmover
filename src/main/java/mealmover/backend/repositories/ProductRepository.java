package mealmover.backend.repositories;

import mealmover.backend.interfaces.ProductRatingDto;
import mealmover.backend.models.ProductModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, UUID> {
    boolean existsByName(String name);
    Optional<ProductModel> findByName(String name);

    @Query("""
        SELECT p as product, AVG(r.rating) as averageRating, COUNT(r) as numberOfReviews
        FROM ProductModel p
        LEFT JOIN p.reviews r
        WHERE LOWER(p.category.name) <> 'drinks'
        GROUP BY p
        ORDER BY averageRating DESC NULLS LAST, numberOfReviews DESC
    """)
    List<ProductRatingDto> findTopRatedFood(Pageable pageable);

    @Query("""
        SELECT p as product, AVG(r.rating) as averageRating, COUNT(r) as numberOfReviews
        FROM ProductModel p
        LEFT JOIN p.reviews r
        WHERE LOWER(p.category.name) = 'drinks'
        GROUP BY p
        ORDER BY averageRating DESC NULLS LAST, numberOfReviews DESC
    """)
    List<ProductRatingDto> findTopRatedDrinks(Pageable pageable);

    @Query("""
        SELECT 
            p as product,
            AVG(r.rating) as averageRating,
            COUNT(r)      as numberOfReviews
        FROM ProductModel p
        LEFT JOIN p.reviews r
        GROUP BY p
        ORDER BY averageRating DESC NULLS LAST, numberOfReviews DESC
    """)
    List<ProductRatingDto> findTopRatedProducts(Pageable pageable);
}