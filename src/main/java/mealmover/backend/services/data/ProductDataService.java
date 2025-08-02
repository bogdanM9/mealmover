package mealmover.backend.services.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.models.ProductModel;
import mealmover.backend.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductDataService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductModel getProductById(UUID productId) {
        log.info("Fetching product with ID: {}", productId);
        return productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
    }
}
