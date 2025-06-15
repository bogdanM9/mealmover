package mealmover.backend.mapper;
import mealmover.backend.dtos.requests.ProductCreateRequestDto;
import mealmover.backend.dtos.responses.ProductResponseDto;
import mealmover.backend.models.ProductModel;
import mealmover.backend.records.ProductData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        SizeMapper.class,
        ReviewMapper.class,
        CategoryMapper.class,
        IngredientMapper.class,
        ExtraIngredientMapper.class
    }
)
public interface ProductMapper {
    ProductModel toModel(ProductCreateRequestDto dto);

    @Mapping(target = "category", ignore = true)
    ProductModel toModel(ProductData data);

    @Mapping(target = "sizes", source = "productSizes")
    ProductResponseDto toDto(ProductModel model);
}
