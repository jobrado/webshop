package hr.algebra.webshop.entity;

public enum Category {
    HANDBAG("Handbag"),
    BACKPACK("Backpack"),
    TOTE("Tote");
    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
