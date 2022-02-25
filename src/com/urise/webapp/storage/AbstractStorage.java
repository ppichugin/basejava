package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage implements Storage {
    public static final Comparator<Resume> COMPARATOR_FULLNAME_THEN_UUID = Comparator.comparing(Resume::getFullname).thenComparing(Resume::getUuid);

    public final void update(Resume r) {
        String uuid = r.getUuid();
        Object searchKey = getKeyForExistedResume(uuid);
        updateResume(r, searchKey);
        Storage.super.update(r); // prints confirmation message to console
    }

    private Object getKeyForExistedResume(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isResumeExist(searchKey)) throw new NotExistStorageException(uuid);
        return searchKey;
    }

    protected abstract Object getSearchKey(String uuid); // @return UUID or INDEX

    protected abstract void updateResume(Resume r, Object searchKey);

    public final void save(Resume r) {
        String uuid = r.getUuid();
        if (isNull(uuid)) return;
        Object searchKey = getKeyForNotExistedResume(uuid);
        insertResume(r, searchKey);
        Storage.super.save(r);
    }

    private boolean isNull(String uuid) {
        if (uuid == null) {
            System.out.println("uuid was not entered. Please try again.");
            return true;
        }
        return false;
    }

    private Object getKeyForNotExistedResume(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isResumeExist(searchKey)) throw new ExistStorageException(uuid);
        return searchKey;
    }

    protected abstract boolean isResumeExist(Object searchKey);

    protected abstract void insertResume(Resume r, Object searchKey);

    public final Resume get(String uuid) {
        Object searchKey = getKeyForExistedResume(uuid);
        System.out.println("OK GET. Resume '" + uuid + "' exists.");
        return getResume(searchKey);
    }

    protected abstract Resume getResume(Object searchKey);

    public final void delete(String uuid) {
        if (isNull(uuid)) return;
        Object searchKey = getKeyForExistedResume(uuid);
        removeResume(searchKey);
        Storage.super.delete(uuid);
    }

    protected abstract void removeResume(Object searchKey);
}
