package mealmover.backend.enums;

public enum Role {
    ADMIN,
    CLIENT,
    DRIVER,
    OPERATOR;


    public String toLower() {
        return this.name().toLowerCase();
    }

    public String toCapitalize() {
        return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
}