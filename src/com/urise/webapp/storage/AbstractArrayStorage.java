package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.out.println("OK GET. Резюме с таким uuid '" + uuid + "' имеется в базе.");
            return storage[index];
        }
        System.out.println("ERROR GET. Резюме с таким uuid '" + uuid + "' нет в базе.");
        return null;
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);
}
