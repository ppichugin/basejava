package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage implements Storage {
    public static final Comparator<Resume> COMPARATOR_FULLNAME_THEN_UUID;

    static {
        COMPARATOR_FULLNAME_THEN_UUID = Comparator
                .comparing(Resume::getFullName)
                .thenComparing(Resume::getUuid);
    }

    public final void update(Resume r) {
        String uuid = r.getUuid();
        Object searchKey = getKeyForExistedResume(uuid);
        updateResume(r, searchKey);
        Storage.super.update(r);
    }

    private Object getKeyForExistedResume(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isResumeExist(searchKey)) throw new NotExistStorageException(uuid);
        return searchKey;
    }

    /**
     * @return UUID or INDEX
     */
    protected abstract Object getSearchKey(String uuid);

    protected abstract void updateResume(Resume r, Object searchKey);

    public final void save(Resume r) {
        String uuid = r.getUuid();
        if (Objects.isNull(uuid)) return;
        Object searchKey = getKeyForNotExistedResume(uuid);
        insertResume(r, searchKey);
        Storage.super.save(r);
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
        return getResume(searchKey);
    }

    protected abstract Resume getResume(Object searchKey);

    public final void delete(String uuid) {
        if (Objects.isNull(uuid)) return;
        Object searchKey = getKeyForExistedResume(uuid);
        removeResume(searchKey);
        Storage.super.delete(uuid);
    }

    public final List<Resume> getAllSorted() {
        List<Resume> list = getList();
        list.sort(COMPARATOR_FULLNAME_THEN_UUID);
        return list;
    }

    protected abstract List<Resume> getList();

    protected abstract void removeResume(Object searchKey);
}
