package mealmover.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@JsonTypeName("operator")
@EqualsAndHashCode(callSuper = true)
public class OperatorResponseDto extends UserResponseDto {}