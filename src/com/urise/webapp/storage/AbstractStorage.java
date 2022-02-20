package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {
        System.out.println("OK. Storage of resume cleared.");
    }

    public final void update(Resume r) {
        String uuid = r.getUuid();
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        updateElement(r, index);
        System.out.println("OK UPDATE. Resume '" + uuid + "' updated.");
    }

    protected abstract int getIndex(String uuid);

    protected abstract void updateElement(Resume r, int index);

    public final void save(Resume r) {
        String uuid = r.getUuid();
        /* if uuid was not entered in command, exit from method */
        if (uuid == null) {
            System.out.println("uuid was not entered. Please try again: save <uuid>");
            return;
        }
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        insertElement(r, index);
        System.out.println("OK SAVE. Resume '" + uuid + "' saved.");
    }

    protected abstract void insertElement(Resume r, int index);

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            System.out.println("OK GET. Resume '" + uuid + "' exists.");
            return getElement(index);
        }
        throw new NotExistStorageException(uuid);
    }

    protected abstract Resume getElement(int index);

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
        removeElement(index);
        System.out.println("OK DELETE. Resume '" + uuid + "' deleted.");
    }

    protected abstract void removeElement(int index);
}
