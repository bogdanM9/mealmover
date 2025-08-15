package mealmover.backend.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.dtos.requests.ReviewCreateRequestDto;
import mealmover.backend.dtos.responses.ReviewResponseDto;
import mealmover.backend.mapper.ReviewMapper;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.ProductModel;
import mealmover.backend.models.ReviewModel;
import mealmover.backend.security.SecurityService;
import mealmover.backend.services.data.ProductDataService;
import mealmover.backend.services.data.ReviewDataService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;
    private final SecurityService securityService;
    private final ReviewDataService reviewDataService;
    private final ProductDataService productDataService;

    public ReviewResponseDto create(ReviewCreateRequestDto requestDto) {
        ClientModel clientModel = (ClientModel) this.securityService.getModelCurrentUser();

        ProductModel productModel = this.productDataService.getById(requestDto.getProductId());

        log.info("Attempting to create a review with data: {}", requestDto);

        ReviewModel review = this.reviewMapper.toModel(requestDto);
        review.setClient(clientModel);
        review.setProduct(productModel);

        ReviewModel savedReview = this.reviewDataService.create(review);

        return this.reviewMapper.toDto(savedReview);
    }

    public List<ReviewResponseDto> getAll() {
        log.info("Attempting to get all reviews");
        return this.reviewDataService
            .getAll()
            .stream()
            .map(this.reviewMapper::toDto)
            .toList();
    }
}