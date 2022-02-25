package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.*;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        super.clear();
    }

    @Override
    protected void updateResume(Resume r, Object index) {
        storage[(Integer) index] = r;
    }

    @Override
    protected void insertResume(Resume r, Object index) {
        checkStorageOverflow(r.getUuid());
        saveToArray(r, (Integer) index);
        size++;
    }

    private void checkStorageOverflow(String uuid) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", uuid);
        }
    }

    @Override
    protected Resume getResume(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void removeResume(Object index) {
        deleteFromArray((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isResumeExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, size)));
        list.sort(COMPARATOR_FULLNAME_THEN_UUID);
        return list;
    }

    @Override
    public int size() {
        return size;
    }

    @Override // Arrays use INDEX
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveToArray(Resume resume, int index);

    protected abstract void deleteFromArray(int index);
}