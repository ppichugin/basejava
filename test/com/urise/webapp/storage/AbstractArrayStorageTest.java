package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        int result = 0;
        storage.clear();
        for (int i = 0; i < ((AbstractArrayStorage) storage).storage.length; i++) {
            if (((AbstractArrayStorage) storage).storage[i] != null) {
                result++;
            }
        }
        Assert.assertEquals(0, result);
    }

    @Test
    public void update() throws Exception {
        Resume resumeBefore = ((AbstractArrayStorage) storage).storage[0];
        storage.update(resumeBefore);
        Resume resumeAfter = ((AbstractArrayStorage) storage).storage[0];
        boolean result = resumeAfter.equals(resumeBefore);
        Assert.assertTrue(result);
    }

    @Test
    public void getAll() throws Exception {
        Resume[] storageAll = storage.getAll();
        Assert.assertEquals(3, storageAll.length);
    }

    @Test
    public void save() throws Exception {
        Resume r = new Resume("uuid4");
        storage.save(r);
        Assert.assertEquals(((AbstractArrayStorage) storage).storage[3], r);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_3);
        Assert.assertNull(((AbstractArrayStorage) storage).storage[2]);
    }

    @Test
    public void get() throws Exception {
        Resume resumeDirectAccess = ((AbstractArrayStorage) storage).storage[0];
        Resume resumeWithGet = storage.get(UUID_1);
        Assert.assertSame(resumeDirectAccess, resumeWithGet);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("dummy"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() throws Exception {
        int end = AbstractArrayStorage.STORAGE_LIMIT;
        try {
            for (int i = 3; i < end; i++) {
                storage.save(new Resume("uuid" + (i + 1)));
            }
        } catch (StorageException e) {
            Assert.fail("Overflow occurred prematurely");
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("dummy");
    }
}