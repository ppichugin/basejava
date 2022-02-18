package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    protected void clearStorageSpecific() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void updateStorageSpecific(int index, Resume r) {
        storage[index] = r;
    }

    @Override
    protected void overflowStorageCheckup(int indexOfResume, String uuid) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", uuid);
        }
    }

    @Override
    protected void saveStorageSpecific(int indexOfResume, Resume r) {
        saveToStorage(indexOfResume, r); //insertElement(r, index);
        size++;
    }

    @Override
    protected Resume getStorageSpecific(int index) {
        return storage[index];
    }

    @Override
    protected void deleteStorageSpecific(int index) {
        deleteAtStorage(index); //fillDeletedElement(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected Resume[] getAllStorageSpecific() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected int sizeStorageSpecific() {
        return size;
    }

    protected abstract int getIndex(String uuid);
    protected abstract void saveToStorage(int index, Resume resume);
    protected abstract void deleteAtStorage(int index);
}