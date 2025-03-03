package mealmover.backend.dtos.requests;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateStatusRequestDto {
    private UUID statusId;
}