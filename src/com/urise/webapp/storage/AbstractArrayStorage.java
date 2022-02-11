package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
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
        System.out.println("OK. Storage of resume cleared.");
    }

    public final void update(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = r;
            System.out.println("OK UPDATE. Resume '" + uuid + "' updated.");
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public final void save(Resume r) {
        String uuid = r.getUuid();
        /* if uuid was not entered in command, exit from method */
        if (uuid == null) {
            System.out.println("uuid was not entered. Please try again: save <uuid>");
            return;
        }
        int indexOfResume = getIndex(uuid);
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else if (indexOfResume < 0) {
            /* if resume not found (i.e. unique), prepare space in storage & save resume */
            saveToStorage(indexOfResume, r); //insertElement(r, index);
            size++;
            System.out.println("OK SAVE. Resume '" + uuid + "' saved.");
        } else {
            throw new ExistStorageException(r.getUuid());
        }

    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.out.println("OK GET. Resume '" + uuid + "' exists.");
            return storage[index];
        }
        throw new NotExistStorageException(uuid);
    }

    public final void delete(String uuid) {
        /* if uuid was not entered in command, exit from method */
        if (uuid == null) {
            System.out.println("uuid was not entered. Please try again: delete <uuid>");
        } else {
            int index = getIndex(uuid);
            /* if resume exists, then delete it */
            if (index >= 0) {
                deleteAtStorage(index); //fillDeletedElement(index);
                storage[size - 1] = null;
                size--;
                System.out.println("OK DELETE. Resume '" + uuid + "' deleted.");
            } else {
                throw new NotExistStorageException(uuid);
            }
        }
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
    protected abstract void saveToStorage(int index, Resume resume);
    protected abstract void deleteAtStorage(int index);
}