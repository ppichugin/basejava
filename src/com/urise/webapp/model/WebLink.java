package com.urise.webapp.model;

import java.io.Serializable;
import java.util.Objects;

public class WebLink implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String name;
    private final String url;

    public WebLink(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebLink webLink = (WebLink) o;
        if (!name.equals(webLink.name)) return false;
        return Objects.equals(url, webLink.url);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        if (url == null) return name;
        return name + ", web: " + url;
    }
}