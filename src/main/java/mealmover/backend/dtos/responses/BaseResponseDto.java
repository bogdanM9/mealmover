package mealmover.backend.dtos.responses;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;
import mealmover.backend.enums.Severity;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDto<T> {
    private final T data;
    private final String message;
    private final String severity;

    protected BaseResponseDto(T data) {
        this.data = data;
        this.message = null;
        this.severity = null;
    }

    protected BaseResponseDto(T data, String message, Severity severity) {
        this.data = data;
        this.message = message;
        this.severity = severity.toLower();
    }

    public static <T> BaseResponseDto<T> of(T data) {
        return new BaseResponseDto<>(data);
    }

    public static <T> BaseResponseDto<T> info(T data, String message) {
        return new BaseResponseDto<>(data, message, Severity.INFO);
    }

    public static <T> BaseResponseDto<T> error(T data, String message) {
        return new BaseResponseDto<>(data, message, Severity.ERROR);
    }

    public static <T> BaseResponseDto<T> success(T data, String message) {
        return new BaseResponseDto<>(data, message, Severity.SUCCESS);
    }

    public static <T> BaseResponseDto<T> warning(T data, String message) {
        return new BaseResponseDto<>(data, message, Severity.WARNING);
    }
}
