/*
package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.OrderCreateRequestDto;
import mealmover.backend.dtos.responses.OrderResponseDto;
import mealmover.backend.models.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClientMapper.class, ProductMapper.class, StatusMapper.class, AddressMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    OrderModel toModel(OrderCreateRequestDto dto);

    OrderResponseDto toDto(OrderModel model);
}
*/
