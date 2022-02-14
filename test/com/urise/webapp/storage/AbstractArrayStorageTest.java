package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

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
        Assert.assertEquals(0, storage.size());
//        /* variant with Reflection */
//        Field size = storage.getClass().getSuperclass().getDeclaredField("size");
//        size.setAccessible(true);
//        Object obj = size.get(storage);
//        Assert.assertEquals(0, obj);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void update() {
        Resume resumeBefore = storage.get(UUID_1);
        storage.update(resumeBefore);
        Resume resumeAfter = storage.get(UUID_1);
        Assert.assertSame(resumeAfter, resumeBefore);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        Assert.assertNotNull(resume);
    }

    @Test
    public void save() {
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void delete() {
        storage.delete(UUID_3);
        Assert.assertEquals(2, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] storageReference = new Resume[3];
        storageReference[0] = storage.get(UUID_1);
        storageReference[1] = storage.get(UUID_2);
        storageReference[2] = storage.get(UUID_3);
        Resume[] storageAll = storage.getAll();
        Assert.assertArrayEquals(storageReference, storageAll);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("dummy"));
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

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("dummy");
    }
}