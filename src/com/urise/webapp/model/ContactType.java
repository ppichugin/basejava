package com.urise.webapp.model;

public enum ContactType {
    PHONE("Telephone"),
    MOBILE("\uD83D\uDCF1 Mobile"),
    SKYPE("Skype"),
    EMAIL("\uE715 E-Mail"),
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