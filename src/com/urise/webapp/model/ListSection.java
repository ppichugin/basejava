package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ListSection extends AbstractSection {
    private final List<String> blocks;

    public ListSection(String... blocks) {
        this(Arrays.asList(blocks));
    }

    public ListSection(List<String> blocks) {
        Objects.requireNonNull(blocks, "blocks must not be null");
        this.blocks = blocks;
    }

    public List<String> getBlocks() {
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
        return blocks.stream()
                .map(String::toString)
                .collect(Collectors.joining("\n"));
    }
}