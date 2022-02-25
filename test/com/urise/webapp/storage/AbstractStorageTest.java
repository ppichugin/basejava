package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume R1 = new Resume(UUID_1, "Ivanov");
    private static final Resume R2 = new Resume(UUID_2, "Petrov");
    private static final Resume R3 = new Resume(UUID_3, "Petrov");
    private static final Resume R4Dummy = new Resume(UUID_4, "dummy");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() {
        Resume resumeNew = R1;
        storage.update(resumeNew);
        Resume resumeUpdated = storage.get(UUID_1);
        assertSame(resumeNew, resumeUpdated);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume());
    }

    @Test
    public void save() {
        final Resume resumeNew = R4Dummy;
        storage.save(resumeNew);
        assertEquals(4, storage.size());
        final Resume resumeObtained = storage.get(UUID_4);
        assertSame(resumeNew, resumeObtained);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R1);
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        int length = AbstractArrayStorage.STORAGE_LIMIT;
        try {
            for (int i = 3; i < length; i++) {
                storage.save(new Resume("uuid" + (i + 1), ""));
            }
        } catch (StorageException e) {
            fail("Overflow occurred prematurely");
        }
        storage.save(new Resume());
    }

    @Test
    public void get() {
        final Resume resumeExpected = R4Dummy;
        storage.save(resumeExpected);
        final Resume resumeReceived = storage.get(UUID_4);
        assertSame(resumeExpected, resumeReceived);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
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
        storage.delete(UUID_4);
    }

    @Test
    public void getAllSorted() {
        List<Resume> storageReference = new ArrayList<>(List.of(R1, R2, R3));
        assertEquals(storageReference, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}