package mealmover.backend.controllers;

import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ReviewCreateRequestDto;
import mealmover.backend.dtos.responses.ReviewResponseDto;
import mealmover.backend.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAll() {
        List<ReviewResponseDto> responseDtos = this.reviewService.getAll();
//        Review
        return ResponseEntity.ok(responseDtos);
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@RequestBody ReviewCreateRequestDto requestDto) {
        ReviewResponseDto responseDto = this.reviewService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}