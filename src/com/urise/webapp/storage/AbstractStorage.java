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
public abstract class AbstractStorage<SK> implements Storage {
    public static final Comparator<Resume> COMPARATOR_FULLNAME_THEN_UUID;

    static {
        COMPARATOR_FULLNAME_THEN_UUID = Comparator
                .comparing(Resume::getFullName)
                .thenComparing(Resume::getUuid);
    }

    public final void update(Resume r) {
        SK searchKey = getKeyForExistedResume(r.getUuid());
        updateResume(r, searchKey);
        Storage.super.update(r);
    }

    /**
     * @return UUID or INDEX
     */
    protected abstract SK getSearchKey(String uuid);

    protected abstract void updateResume(Resume r, SK searchKey);

    public final void save(Resume r) {
        String uuid = r.getUuid();
        if (Objects.isNull(uuid)) return;
        SK searchKey = getKeyForNotExistedResume(uuid);
        insertResume(r, searchKey);
        Storage.super.save(r);
    }

    private SK getKeyForNotExistedResume(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isResumeExist(searchKey)) throw new ExistStorageException(uuid);
        return searchKey;
    }

    protected abstract boolean isResumeExist(SK searchKey);

    protected abstract void insertResume(Resume r, SK searchKey);

    public final Resume get(String uuid) {
        SK searchKey = getKeyForExistedResume(uuid);
        return getResume(searchKey);
    }

    protected abstract Resume getResume(SK searchKey);

    public final void delete(String uuid) {
        if (Objects.isNull(uuid)) return;
        SK searchKey = getKeyForExistedResume(uuid);
        removeResume(searchKey);
        Storage.super.delete(uuid);
    }

    public final List<Resume> getAllSorted() {
        List<Resume> list = getCopyAll();
        list.sort(COMPARATOR_FULLNAME_THEN_UUID);
        return list;
    }

    protected abstract List<Resume> getCopyAll();

    protected abstract void removeResume(SK searchKey);

    private SK getKeyForExistedResume(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isResumeExist(searchKey)) throw new NotExistStorageException(uuid);
        return searchKey;
    }
}
