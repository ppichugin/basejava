package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage<SK> implements Storage {
    //    protected final Logger LOG = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    public static final Comparator<Resume> COMPARATOR_FULLNAME_THEN_UUID;

    static {
        COMPARATOR_FULLNAME_THEN_UUID = Comparator
                .comparing(Resume::getFullName)
                .thenComparing(Resume::getUuid);
    }

    public final void update(Resume r) {
        LOG.info("Update " + r);
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
        LOG.info("Save " + r);
        String uuid = r.getUuid();
        if (Objects.isNull(uuid)) return;
        SK searchKey = getKeyForNotExistedResume(uuid);
        insertResume(r, searchKey);
        Storage.super.save(r);
    }

    private SK getKeyForNotExistedResume(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (isResumeExist(searchKey)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract boolean isResumeExist(SK searchKey);

    protected abstract void insertResume(Resume r, SK searchKey);

    public final Resume get(String uuid) {
        LOG.info("Get " + uuid);
        SK searchKey = getKeyForExistedResume(uuid);
        return getResume(searchKey);
    }

    protected abstract Resume getResume(SK searchKey);

    public final void delete(String uuid) {
        LOG.info("Delete " + uuid);
        if (Objects.isNull(uuid)) return;
        SK searchKey = getKeyForExistedResume(uuid);
        removeResume(searchKey);
        Storage.super.delete(uuid);
    }

    public final List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = getCopyAll();
        list.sort(COMPARATOR_FULLNAME_THEN_UUID);
        return list;
    }

    protected abstract List<Resume> getCopyAll();

    protected abstract void removeResume(SK searchKey);

    private SK getKeyForExistedResume(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isResumeExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
