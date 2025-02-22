package mealmover.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private String id;
    private String title;
    private float rating;
    private String text;
    private String postedAt;
    private String updatedAt;
    private ProductResponseDto product;
    private ClientResponseDto client;
}
