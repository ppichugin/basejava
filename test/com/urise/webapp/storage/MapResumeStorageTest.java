package com.urise.webapp.storage;

import org.junit.Test;

public class MapResumeStorageTest extends AbstractStorageTest {

    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Override
    @Test
    public void saveStorageOverflow() {
        System.out.println("HashMap is conditionally dimensionless.");
    }
}