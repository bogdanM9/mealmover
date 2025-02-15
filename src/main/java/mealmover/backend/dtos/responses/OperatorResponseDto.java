package mealmover.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OperatorResponseDto extends UserResponseDto {
    private UUID id;
    private String email;
}
