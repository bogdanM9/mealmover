package mealmover.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@JsonSubTypes({
    @JsonSubTypes.Type(value = AdminResponseDto.class, name = "admin"),
    @JsonSubTypes.Type(value = ClientResponseDto.class, name = "client"),
    @JsonSubTypes.Type(value = DriverResponseDto.class, name = "driver"),
    @JsonSubTypes.Type(value = OperatorResponseDto.class, name = "operator")
})
public class UserResponseDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private RoleResponseDto role;
}
