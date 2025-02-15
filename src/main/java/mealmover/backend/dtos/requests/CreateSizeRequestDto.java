package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSizeRequestDto {
    private String name;
    private float price;
    private int weight;
}
