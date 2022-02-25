package com.urise.webapp.storage;

import org.junit.Test;

public class MapUuidStorageTest extends AbstractStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Override
    @Test
    public void saveStorageOverflow() {
        System.out.println("HashMap is conditionally dimensionless.");
    }
}