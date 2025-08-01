package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.ReviewCreateRequestDto;
import mealmover.backend.dtos.responses.ReviewResponseDto;
import mealmover.backend.mapper.ReviewMapper;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.ProductModel;
import mealmover.backend.models.ReviewModel;
import mealmover.backend.repositories.ReviewRepository;
import mealmover.backend.security.SecurityService;
import mealmover.backend.services.data.ProductDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final SecurityService securityService;
    private final ReviewRepository repository;

    private final ProductDataService productService;
    private final ReviewMapper mapper;

    public ReviewResponseDto create(ReviewCreateRequestDto requestDto) {
        ClientModel clientModel = (ClientModel) this.securityService.getModelCurrentUser();
        ProductModel productModel = productService.getProductById(requestDto.getProductId());

        log.info("Attempting to create a review with data: {}", requestDto);

        ReviewModel review = this.mapper.toModel(requestDto);
        review.setClient(clientModel);
        review.setProduct(productModel);

        ReviewModel savedReview = this.repository.save(review);

        return this.mapper.toDto(savedReview);
    }


    public List<ReviewResponseDto> getAll() {
        log.info("Attempting to get all reviews");

        return this.repository.findAll().stream()
                .map(this.mapper::toDto)
                .toList();
    }

    public List<ReviewModel> getReviewsByProductId(UUID id) {
        log.info("Attempting to get all reviews for product with id: {}", id);

        return this.repository.findByProductId(id);
    }

    public boolean existsByClientIdAndProductId(UUID clientId, UUID productId) {
        log.info("Checking if review exists for client with id: {} and product with id: {}", clientId, productId);

        return this.repository.existsByClientIdAndProductId(clientId, productId);
    }
}