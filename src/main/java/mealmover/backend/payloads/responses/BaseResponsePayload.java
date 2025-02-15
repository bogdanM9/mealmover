package mealmover.backend.payloads.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import mealmover.backend.enums.Severity;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseResponsePayload<T> {
    @JsonIgnore
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

    protected abstract Object getResponseData();

    public static <T, R extends BaseResponsePayload<T>> R of(T data, Class<R> responseClass) {
        try {
            return responseClass.getDeclaredConstructor(data.getClass()).newInstance(data);
        } catch(Exception e) {
            throw new RuntimeException("Failed to create response payload", e);
        }
    }

    public static <T, R extends BaseResponsePayload<T>> R info(T data, String message, Class<R> responseClass) {
        try {
            return responseClass
                .getDeclaredConstructor(data.getClass(), String.class, Severity.class)
                .newInstance(data, message, Severity.INFO);
        } catch(Exception e) {
            throw new RuntimeException("Failed to create response payload", e);
        }
    }

    public static <T, R extends BaseResponsePayload<T>> R error(T data, String message, Class<R> responseClass) {
        try {
            return responseClass
                .getDeclaredConstructor(data.getClass(), String.class, Severity.class)
                .newInstance(data, message, Severity.ERROR);
        } catch(Exception e) {
            throw new RuntimeException("Failed to create response payload", e);
        }
    }

    public static <T, R extends BaseResponsePayload<T>> R warning(T data, String message, Class<R> responseClass) {
        try {
            return responseClass
                .getDeclaredConstructor(data.getClass(), String.class, Severity.class)
                .newInstance(data, message, Severity.WARNING);
        } catch(Exception e) {
            throw new RuntimeException("Failed to create response payload", e);
        }
    }

    public static <T, R extends BaseResponsePayload<T>> R success(T data, String message, Class<R> responseClass) {
        try {
            return responseClass
                .getDeclaredConstructor(data.getClass(), String.class, Severity.class)
                .newInstance(data, message, Severity.SUCCESS);
        } catch(Exception e) {
            throw new RuntimeException("Failed to create response payload", e);
        }
    }
}
