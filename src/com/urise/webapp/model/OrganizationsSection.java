package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrganizationsSection extends AbstractSection {
    private final List<Organization> organizations;

    public OrganizationsSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationsSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationsSection that = (OrganizationsSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.stream()
                .map(Organization::toString)
                .collect(Collectors.joining("\n"));
    }
}