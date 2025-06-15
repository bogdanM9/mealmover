package mealmover.backend.dtos.responses;


import lombok.Getter;
import mealmover.backend.enums.Severity;

@Getter
public class MessageResponseDto extends BaseResponseDto<Void> {
    public MessageResponseDto(String message, Severity severity) {
        super(null, message, severity);
    }

    public static MessageResponseDto success(String message) {
        return new MessageResponseDto(message, Severity.SUCCESS);
    }

    public static MessageResponseDto info(String message) {
        return new MessageResponseDto(message, Severity.INFO);
    }
}

