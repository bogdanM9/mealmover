package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReviewCreateRequestDto {
    private String title;
    private float rating;
    private String text;
    private UUID productId;
}
