package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;
import mealmover.backend.models.ProductModel;
import mealmover.backend.models.SizeModel;

@Getter
@Setter
public class ProductSizeCreateRequestDto {
    private SizeModel sizeModel;
    private ProductModel productModel;
}
