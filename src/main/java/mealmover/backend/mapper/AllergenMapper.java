package mealmover.backend.mapper;

import mealmover.backend.dtos.AllergenCreateDto;
import mealmover.backend.dtos.responses.AllergenResponseDto;
import mealmover.backend.models.AllergenModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AllergenMapper {
    @Mapping(target = "id", ignore = true)
    AllergenModel toModel (AllergenCreateDto dto);

    AllergenResponseDto toDto (AllergenModel model);
}
