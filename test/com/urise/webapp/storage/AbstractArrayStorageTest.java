package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resumeNew = new Resume(UUID_1);
        storage.update(resumeNew);
        Resume resumeUpdated = storage.get(UUID_1);
        assertSame(resumeNew, resumeUpdated);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
    }

    @Test
    public void save() {
        final Resume resumeNew = new Resume("uuid4");
        storage.save(resumeNew);
        assertEquals(4, storage.size());
        final Resume resumeObtained = storage.get("uuid4");
        assertSame(resumeNew, resumeObtained);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        int length = AbstractArrayStorage.STORAGE_LIMIT;
        try {
            for (int i = 3; i < length; i++) {
                storage.save(new Resume("uuid" + (i + 1)));
            }
        } catch (StorageException e) {
            fail("Overflow occurred prematurely");
        }
        storage.save(new Resume());
    }

    @Test
    public void get() {
        final Resume resumeExpected = new Resume("uuid4");
        storage.save(resumeExpected);
        final Resume resumeReceived = storage.get("uuid4");
        assertSame(resumeExpected, resumeReceived);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        assertEquals(2, storage.size());

        try {
            storage.get(UUID_3);
        } catch (NotExistStorageException e) {
            System.out.println("OK " + e.getMessage());
        }
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }

    @Test
    public void getAll() {
        Resume[] storageReference = new Resume[]{new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Resume[] storageAll = storage.getAll();
        Arrays.sort(storageAll);
        assertArrayEquals(storageReference, storageAll);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}