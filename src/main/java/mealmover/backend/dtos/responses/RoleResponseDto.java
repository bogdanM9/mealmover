package mealmover.backend.dtos.responses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
public class RoleResponseDto {
    private UUID id;
    private String name;
}
