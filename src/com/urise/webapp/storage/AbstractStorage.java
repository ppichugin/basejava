package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public final void clear() {
        clearStorageSpecific();
        System.out.println("OK. Storage of resume cleared.");
    }

    protected abstract void clearStorageSpecific();


    public final void update(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        updateStorageSpecific(index, r);
        System.out.println("OK UPDATE. Resume '" + uuid + "' updated.");
    }

    protected abstract int getIndex(String uuid);

    protected abstract void updateStorageSpecific(int index, Resume r);

    public final void save(Resume r) {
        String uuid = r.getUuid();
        /* if uuid was not entered in command, exit from method */
        if (uuid == null) {
            System.out.println("uuid was not entered. Please try again: save <uuid>");
            return;
        }
        int index = getIndex(uuid);
        overflowStorageCheckup(index, uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        saveStorageSpecific(index, r);
        System.out.println("OK SAVE. Resume '" + uuid + "' saved.");
    }

    protected abstract void overflowStorageCheckup(int index, String uuid);

    protected abstract void saveStorageSpecific(int index, Resume r);


    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.out.println("OK GET. Resume '" + uuid + "' exists.");
            return getStorageSpecific(index);
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract Resume getStorageSpecific(int index);


    public final void delete(String uuid) {
        /* if uuid was not entered in command, exit from method */
        if (uuid == null) {
            System.out.println("uuid was not entered. Please try again: delete <uuid>");
            return;
        }
        int index = getIndex(uuid);
        /* if resume exists, then delete it */
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deleteStorageSpecific(index);
        System.out.println("OK DELETE. Resume '" + uuid + "' deleted.");
    }

    protected abstract void deleteStorageSpecific(int index);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public final Resume[] getAll() {
        return getAllStorageSpecific();
    }

    protected abstract Resume[] getAllStorageSpecific();


    public final int size() {
        return sizeStorageSpecific();
    }

    protected abstract int sizeStorageSpecific();
}
