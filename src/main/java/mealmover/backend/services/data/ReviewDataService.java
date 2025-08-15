package mealmover.backend.services.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mealmover.backend.constants.PendingClientConstants;
import mealmover.backend.exceptions.NotFoundException;
import mealmover.backend.models.PendingClientModel;
import mealmover.backend.models.ReviewModel;
import mealmover.backend.repositories.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewDataService {
    private final ReviewRepository reviewRepository;

    @Transactional
    public ReviewModel create(ReviewModel model) {
        log.info("Attempting to create review");

        ReviewModel savedModel = this.reviewRepository.save(model);

        log.info("Successfully created review");

        return savedModel;
    }

    @Transactional(readOnly = true)
    public List<ReviewModel> getAll() {
        log.info("Getting all reviews");
        return this.reviewRepository.findAll();
    }
}