package mealmover.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SizeCreateDto {
    private String name;
    private float price;
    private int weight;
}
