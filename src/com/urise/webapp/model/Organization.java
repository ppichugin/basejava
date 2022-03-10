package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organization {
    private final WebLink site;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String jobTitle;
    private final String responsibilities;

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String jobTitle, String responsibilities) {
        this.site = new WebLink(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobTitle = jobTitle;
        this.responsibilities = responsibilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        if (!Objects.equals(site, that.site)) return false;
        if (!startDate.equals(that.startDate)) return false;
        if (!endDate.equals(that.endDate)) return false;
        if (!jobTitle.equals(that.jobTitle)) return false;
        return Objects.equals(responsibilities, that.responsibilities);
    }

    @Override
    public int hashCode() {
        int result = site.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + jobTitle.hashCode();
        result = 31 * result + (responsibilities != null ? responsibilities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "site=" + site +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", jobTitle='" + jobTitle + '\'' +
                ", responsibilities='" + responsibilities + '\'' +
                '}';
    }
}