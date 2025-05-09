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
    private final ProductSizeRepository repository;

    public ProductSizeModel getModelById(UUID id) {
        return this.repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Product size not found"));
    }
}
