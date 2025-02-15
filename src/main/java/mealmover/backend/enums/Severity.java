package mealmover.backend.enums;

public enum Severity {
    INFO,
    ERROR,
    WARNING,
    SUCCESS;

    public String toLower() {
        return this.name().toLowerCase();
    }
}