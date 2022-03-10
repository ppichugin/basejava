package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Objects;

public class ListSection extends Section {
    private final ArrayList<String> blocks;

    public ListSection(ArrayList<String> blocks) {
        Objects.requireNonNull(blocks, "parts of sections must not be null");
        this.blocks = blocks;
    }

    public ArrayList<String> getBlocks() {
        return blocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return blocks.equals(that.blocks);
    }

    @Override
    public int hashCode() {
        return blocks.hashCode();
    }

    @Override
    public String toString() {
        return blocks.toString();
    }
}