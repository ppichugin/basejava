package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Period {
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String jobTitle;
    private final String responsibilities;

    public Period(LocalDate startDate, LocalDate endDate, String jobTitle, String responsibilities) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(jobTitle, "jobTitle must not be null");
        this.startDate = startDate;
        this.endDate = endDate;
        this.jobTitle = jobTitle;
        this.responsibilities = responsibilities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        if (!startDate.equals(period.startDate)) return false;
        if (!endDate.equals(period.endDate)) return false;
        if (!jobTitle.equals(period.jobTitle)) return false;
        return Objects.equals(responsibilities, period.responsibilities);
    }

    @Override
    public int hashCode() {
        int result = startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + jobTitle.hashCode();
        result = 31 * result + (responsibilities != null ? responsibilities.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final String baseLine = startDate.format(DateTimeFormatter.ofPattern("MM/yyyy")) + " - " +
                endDate.format(DateTimeFormatter.ofPattern("MM/yyyy")) + '\t' + jobTitle;
        if (responsibilities == null) {
            return String.join("", baseLine);
        }
        return String.join("",
                baseLine + '\n' + responsibilities);
    }
}