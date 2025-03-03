package mealmover.backend.dtos.responses;

import mealmover.backend.enums.Severity;

public class ErrorResponseDto extends BaseResponseDto<Void> {
    public ErrorResponseDto(String message) {
        super(null, message, Severity.ERROR);
    }
}