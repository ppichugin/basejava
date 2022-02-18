package com.urise.webapp.storage;

import org.junit.Test;

public class ListStorageTest extends AbstractArrayStorageTest {

    public ListStorageTest() {
        super(new ListStorage());
    }

    @Override
    @Test
    public void saveStorageOverflow() {
        System.out.println("ArrayList is conditionally dimensionless.");
    }
}