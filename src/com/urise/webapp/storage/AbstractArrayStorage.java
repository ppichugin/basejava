package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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
    protected void updateResume(Resume r, Integer index) {
        storage[index] = r;
    }

    @Override
    protected void insertResume(Resume r, Integer index) {
        checkStorageOverflow(r.getUuid());
        saveToArray(r, index);
        size++;
    }

    private void checkStorageOverflow(String uuid) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", uuid);
        }
    }

    @Override
    protected Resume getResume(Integer index) {
        return storage[index];
    }

    @Override
    protected void removeResume(Integer index) {
        deleteFromArray(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isResumeExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOf(storage, size)));
    }

    @Override
    public int size() {
        return size;
    }

    /* Arrays use INDEX */
    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void saveToArray(Resume resume, int index);

    protected abstract void deleteFromArray(int index);
}