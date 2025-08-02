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
        return ResponseEntity.ok(this.reviewService.getAll());
    }

    @PostMapping
    public ResponseEntity<ReviewResponseDto> create(@RequestBody ReviewCreateRequestDto requestDto) {
        ReviewResponseDto responseDto = this.reviewService.create(requestDto);
        return ResponseEntity.ok(responseDto);
    }


}
