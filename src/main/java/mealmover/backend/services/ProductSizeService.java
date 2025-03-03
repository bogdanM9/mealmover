package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.models.ProductSizeModel;
import mealmover.backend.repositories.ProductSizeRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductSizeService {
    private final ProductService productService;
    private final SizeService sizeService;
    private final ProductSizeRepository productSizeRepository;

    public ProductSizeModel getModelById(UUID id) {
        return this.productSizeRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product size not found"));
    }
}
