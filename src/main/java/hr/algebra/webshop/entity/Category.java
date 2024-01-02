package hr.algebra.webshop.entity;

import lombok.Data;
import lombok.Getter;

@Getter
public enum Category {
    HANDBAG("Handbag"),
    BACKPACK("Backpack"),
    TOTE("Tote");
    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }


}
