package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = new File("C:\\MyJava\\basejava\\storage");

    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final Resume R1 = ResumeTestData.createResume(UUID_1, "Ivanov");
    private static final Resume R2 = ResumeTestData.createResume(UUID_2, "Petrov");
    private static final Resume R3 = ResumeTestData.createResume(UUID_3, "Petrov");

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
        Resume resumeNew = ResumeTestData.createResume(UUID_1, "Ivanov");
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
        final Resume resumeNew = ResumeTestData.createResume(UUID_4, "dummy");
        storage.save(resumeNew);
        assertEquals(4, storage.size());
        assertEquals(resumeNew, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R1);
    }

    @Test
    public void get() {
        assertEquals(R1, storage.get(UUID_1));
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
        List<Resume> expected = new ArrayList<>(List.of(R1, R2, R3));
        assertEquals(3, storage.size());
        assertEquals(expected, storage.getAllSorted());
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}