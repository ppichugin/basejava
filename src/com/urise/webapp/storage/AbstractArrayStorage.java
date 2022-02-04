package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("ОК. База резюме очищена.");
    }

    public void update(Resume r) {
        String uuid = r.getUuid();
        /* если такое резюме есть в базе, то обновляем его */
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = r;
            System.out.println("ОК UPDATE. Резюме '" + uuid + "' обновлено.");
        } else {
            System.out.println("ERROR UPDATE: Резюме '" + uuid + "' в базе нет.");
        }
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.out.println("OK GET. Резюме с таким uuid '" + uuid + "' имеется в базе.");
            return storage[index];
        }
        System.out.println("ERROR GET. Резюме с таким uuid '" + uuid + "' нет в базе.");
        return null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);
}