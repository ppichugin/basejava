package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListPeriod {
    private final List<Period> periods;

    public ListPeriod(List<Period> periods) {
        Objects.requireNonNull(periods, "periods must not be null");
        this.periods = periods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListPeriod that = (ListPeriod) o;
        return periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return periods.hashCode();
    }

    @Override
    public String toString() {
        return periods.stream()
                .map(Period::toString)
                .collect(Collectors.joining("\n"));
    }
}
