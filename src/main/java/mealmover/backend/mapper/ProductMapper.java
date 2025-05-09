package mealmover.backend.mapper;
import mealmover.backend.dtos.ProductCreateDto;
import mealmover.backend.dtos.responses.ProductResponseDto;
import mealmover.backend.models.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {
        SizeMapper.class,
        CategoryMapper.class,
        IngredientMapper.class,
        ExtraIngredientMapper.class,
        ReviewMapper.class
    }
)
public interface ProductMapper {
    ProductModel toModel(ProductCreateDto dto);

    @Mapping(target = "sizes", source = "productSizes")
    ProductResponseDto toDto(ProductModel model);
}
