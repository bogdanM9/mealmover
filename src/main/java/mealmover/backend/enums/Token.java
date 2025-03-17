package mealmover.backend.enums;

import mealmover.backend.utils.StringUtils;

public enum Token {
    ACCESS,
    RESET_PASSWORD,
    ACTIVATE_CLIENT,
    CHANGE_EMAIL;

    public String toCamelCase() {
        return StringUtils.toCamelCase(this.name()) + "Token";
    }
}