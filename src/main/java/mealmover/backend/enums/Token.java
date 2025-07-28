package mealmover.backend.enums;

import mealmover.backend.utils.StringUtils;

public enum Token {
    ACCESS,
    ACTIVATE,
    CHANGE_EMAIL,
    RESET_PASSWORD;

    @Override
    public String toString() {
        return StringUtils.toCamelCase(this.name()) + "Token";
    }
}