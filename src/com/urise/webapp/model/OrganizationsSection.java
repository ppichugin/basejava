package com.urise.webapp.model;

import java.util.ArrayList;

public class OrganizationsSection extends Section {
    private final ArrayList<Organization> organizations;

    public OrganizationsSection(ArrayList<Organization> organizations) {
        this.organizations = organizations;
    }

    public ArrayList<Organization> getOrganizations() {
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
        return organizations.toString();
    }
}