package mealmover.backend.responses;

import lombok.Getter;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.ToString;
import mealmover.backend.enums.Severity;

@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponsePayload<T> {
    private final T data;
    private final String message;
    private final String severity;

    protected BaseResponsePayload(T data) {
        this.data = data;
        this.message = null;
        this.severity = null;
    }

    protected BaseResponsePayload(T data, String message, Severity severity) {
        this.data = data;
        this.message = message;
        this.severity = severity.toLower();
    }

    public static <T> BaseResponsePayload<T> of(T data) {
        return new BaseResponsePayload<>(data);
    }

    public static <T> BaseResponsePayload<T> info(T data, String message) {
        return new BaseResponsePayload<>(data, message, Severity.INFO);
    }

    public static <T> BaseResponsePayload<T> error(T data, String message) {
        return new BaseResponsePayload<>(data, message, Severity.ERROR);
    }

    public static <T> BaseResponsePayload<T> success(T data, String message) {
        return new BaseResponsePayload<>(data, message, Severity.SUCCESS);
    }

    public static <T> BaseResponsePayload<T> warning(T data, String message) {
        return new BaseResponsePayload<>(data, message, Severity.WARNING);
    }
}
