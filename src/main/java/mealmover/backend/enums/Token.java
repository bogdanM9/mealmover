package mealmover.backend.enums;

import mealmover.backend.utils.StringUtils;

public enum Token {
    ACCESS,          // Regular authentication token
    REGISTRATION_CLIENT,    // Account activation token
    APPLICATION,     // Doctor application token
    PASSWORD_RESET;   // Password reset token

    public String toConvert() {
        return StringUtils.toConvert(this.name());
    }

    public static Token fromConvert(String str) {
        return Token.valueOf(StringUtils.fromConvert(str));
    }

    @Override
    public String toString() {
        return StringUtils.toCamelCase(this.name()) + "Token";
    }
}