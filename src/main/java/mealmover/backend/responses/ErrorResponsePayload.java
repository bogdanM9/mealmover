package mealmover.backend.responses;

import mealmover.backend.enums.Severity;

public class ErrorResponsePayload extends BaseResponsePayload<Void>{
    public ErrorResponsePayload(String message) {
        super(null, message, Severity.ERROR);
    }
}