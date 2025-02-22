package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ReviewCreateRequestDto;
import mealmover.backend.dtos.responses.ReviewResponseDto;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.mapper.ReviewMapper;
import mealmover.backend.messages.ReviewMessages;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.ProductModel;
import mealmover.backend.models.ReviewModel;
import mealmover.backend.repositories.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repository;

    private final ReviewMapper mapper;

    private final ClientService clientService;

    private final ProductService productService;

    private final ReviewMessages messages;

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    public ReviewResponseDto create(ReviewCreateRequestDto requestDto) {
        UUID clientId = requestDto.getClientId();
        UUID productId = requestDto.getProductId();

        logger.info("Attempting to create a review for client with id: {} and product with id: {}", clientId, productId);

//        if (!this.clientService.existsById(clientId)) {
//            throw new NotFoundException(this.messages.notFoundById());
//        }
//
//        if (!this.productService.existsById(productId)) {
//            throw new NotFoundException(this.messages.notFoundById();
//        }

        ClientModel client = this.clientService.getModelById(clientId);
        ProductModel product = this.productService.getModelById(productId);

        ReviewModel review = this.mapper.toModel(requestDto);

        review.setClient(client);
        review.setProduct(product);

        ReviewModel savedReview = this.repository.save(review);

        return this.mapper.toDto(savedReview);
    }

    public List<ReviewResponseDto> getAll() {
        logger.info("Attempting to get all reviews");

        return this.repository.findAll().stream()
                .map(this.mapper::toDto)
                .toList();
    }

}
