package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationsSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private List<Organization> organizations;

    public OrganizationsSection() {
    }

    public OrganizationsSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationsSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
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