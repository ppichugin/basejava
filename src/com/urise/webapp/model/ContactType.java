package com.urise.webapp.model;

public enum ContactType {
    PHONE("Telephone"),
    MOBILE("Mobile"),
    SKYPE("Skype"),
    EMAIL("E-Mail"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackOverflow"),
    WEB("Personal homepage");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
