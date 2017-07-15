package com.gihan.model;


public enum Store {
    ALDI("ALDI"),
    IGA("IGA"),
    GREEN_GROCER("GREEN GROCER"),
    UDAYA("UDAYA"),
    UNKNOWN("UNKNOWN");

    Store(String displayName) {
        this.displayName = displayName;
    }

    private String displayName;

    public String getDisplayName() {
        return displayName;
    }
}
