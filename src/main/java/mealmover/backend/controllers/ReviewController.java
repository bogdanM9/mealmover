package mealmover.backend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mealmover.backend.dtos.requests.ReviewCreateRequestDto;
import mealmover.backend.dtos.responses.ReviewResponseDto;
import mealmover.backend.messages.ReviewMessages;
import mealmover.backend.services.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    private final ReviewMessages reviewMessages;


    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@Valid @RequestBody ReviewCreateRequestDto requestDto) {
        ReviewResponseDto response = this.reviewService.create(requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ReviewResponseDto>> getAll() {
        return ResponseEntity.ok(this.reviewService.getAll());
    }


}
