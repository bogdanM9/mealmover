package mealmover.backend.dtos.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class PendingClientResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
}
