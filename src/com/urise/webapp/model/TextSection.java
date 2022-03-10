package com.urise.webapp.model;

public class TextSection extends Section {
    private final String topic;

    public TextSection(String topic) {
        this.topic = topic;
    }

    public String getTopic() {
        return topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return topic.equals(that.topic);
    }

    @Override
    public int hashCode() {
        return topic.hashCode();
    }

    @Override
    public String toString() {
        return topic;
    }
}
