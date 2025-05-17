package mealmover.backend.dtos.responses;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonTypeName("driver")
@EqualsAndHashCode(callSuper = true)
public class DriverResponseDto extends UserResponseDto {}
