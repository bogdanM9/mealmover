package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.ReviewCreateRequestDto;
import mealmover.backend.dtos.responses.ReviewResponseDto;
import mealmover.backend.models.ProductModel;
import mealmover.backend.models.ReviewModel;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(
    componentModel = "spring", uses = { ProductMapper.class, ClientMapper.class }
)
public interface ReviewMapper {

    ReviewModel toModel(ReviewCreateRequestDto dto);

    ReviewResponseDto toDto(ReviewModel model);

    ProductModel toProductModel(UUID productId);
}
