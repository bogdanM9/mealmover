package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ReviewCreateRequestDto {
    private String text;
    private String title;
    private float rating;
    private UUID productId;
}
