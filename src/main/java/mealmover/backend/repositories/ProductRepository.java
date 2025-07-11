package mealmover.backend.repositories;

import mealmover.backend.models.ProductModel;
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

    @Query(value = """
        SELECT p.* FROM products p
        JOIN product_sizes ps ON p.id = ps.product_id
        JOIN orders_products op ON ps.id = op.product_size_id
        GROUP BY p.id
        ORDER BY SUM(op.quantity) DESC
        LIMIT 4
    """, nativeQuery = true)
    List<ProductModel> findTopFourBestSellingProducts();

    @Query(value = """
        SELECT p.* FROM products p
        JOIN product_sizes ps ON p.id = ps.product_id
        JOIN orders_products op ON ps.id = op.product_size_id
        WHERE p.category_id = :categoryId
        GROUP BY p.id
        ORDER BY SUM(op.quantity) DESC
        LIMIT 4
        """, nativeQuery = true)
    List<ProductModel> findTopFourBestSellingProductsByCategoryId(@Param("categoryId") UUID categoryId);

    @Query(value = """
        SELECT p.* FROM products p
        JOIN reviews r ON p.id = r.product_id
        GROUP BY p.id
        ORDER BY COUNT(r.id) DESC
        LIMIT 4
    """, nativeQuery = true)
    List<ProductModel> findTop4ReviewedFoods();

    @Query
(value = """
        SELECT p.* FROM products p
        JOIN reviews r ON p.id = r.product_id
        WHERE p.category_id = :categoryId
        GROUP BY p.id
        ORDER BY COUNT(r.id) DESC
        LIMIT 4
    """, nativeQuery = true)
    List<ProductModel> findTop4ReviewedDrinks();
}
