package mealmover.backend.enums;

import mealmover.backend.utils.StringUtils;

public enum Role {
    ADMIN,
    CLIENT,
    DRIVER,
    OPERATOR;

    public String toConvert() {
        return StringUtils.toConvert(this.name());
    }

    public static Role fromConvert(String str) {
        return Role.valueOf(StringUtils.fromConvert(str));
    }
}