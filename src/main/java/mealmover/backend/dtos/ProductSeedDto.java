package mealmover.backend.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductSeedDto {
    private String name;
    private String category;
}