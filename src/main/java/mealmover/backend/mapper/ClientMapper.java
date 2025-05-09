package mealmover.backend.mapper;

import mealmover.backend.dtos.requests.ClientCreateRequestDto;
import mealmover.backend.dtos.responses.ClientResponseDto;
import mealmover.backend.models.ClientModel;
import mealmover.backend.models.PendingClientModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
    RoleMapper.class,
    AddressMapper.class,
    CreditCardMapper.class
})
public interface ClientMapper {
    @Mapping(target = "id", ignore = true)
    ClientModel toModel(PendingClientModel model);

    @Mapping(source="model.roles", target="role", qualifiedByName = "toFirstDto")
    ClientResponseDto toDto (ClientModel model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "addresses", ignore = true)
    @Mapping(target = "creditCards", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "orders", ignore = true)
    ClientModel toModel (ClientCreateRequestDto dto);
}