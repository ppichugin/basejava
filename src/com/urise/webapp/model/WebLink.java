package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class WebLink implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String url;

    public WebLink() {
    }

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