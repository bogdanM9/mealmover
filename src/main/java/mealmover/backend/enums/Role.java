package mealmover.backend.enums;

import mealmover.backend.utils.StringUtils;

public enum Role {
    CLIENT, ADMIN;

    public String toCapitalize() {
        return StringUtils.toCapitalize(this.name());
    }

    public static Role fromCapitalize(String str) { // Client -> CLIENT
        return Role.valueOf(StringUtils.fromCapitalize(str));
    }
}