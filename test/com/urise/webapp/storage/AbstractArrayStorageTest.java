package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        int length = AbstractArrayStorage.STORAGE_LIMIT;
        try {
            for (int i = 3; i < length; i++) {
                super.storage.save(new Resume("uuid" + (i + 1), ""));
            }
        } catch (StorageException e) {
            fail("Overflow occurred prematurely");
        }
        super.storage.save(new Resume("Petrov"));
    }
}