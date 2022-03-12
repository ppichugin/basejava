package com.urise.webapp.model;

import java.util.Objects;

public class Organization {
    private final WebLink site;
    private final ListPeriod periodList;

    public Organization(String name, String url, ListPeriod periodList) {
        Objects.requireNonNull(periodList, "periodList must not be null");
        this.site = new WebLink(name, url);
        this.periodList = periodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        if (!site.equals(that.site)) return false;
        return periodList.equals(that.periodList);
    }

    @Override
    public int hashCode() {
        int result = site.hashCode();
        result = 31 * result + periodList.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.join("", site + "\n" + periodList + "\n");
    }
}