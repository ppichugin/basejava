package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

/**
 * Abstract storage for Resumes
 */
public abstract class AbstractStorage implements Storage {

    public void clear() {
        writeConfirmationToConsole("CLEARED", "");
    }

    private void writeConfirmationToConsole(String status, String uuid) {
        if (status == null) return;
        switch (status) {
            case "CLEARED" -> System.out.println("OK. Storage of resume cleared.");
            case "UPDATED" -> System.out.println("OK UPDATE. Resume '" + uuid + "' updated.");
            case "SAVED" -> System.out.println("OK SAVE. Resume '" + uuid + "' saved.");
            case "EXIST" -> System.out.println("OK GET. Resume '" + uuid + "' exists.");
            case "DELETED" -> System.out.println("OK DELETE. Resume '" + uuid + "' deleted.");
            default -> System.out.println();
        }
    }

    public final void update(Resume r) {
        String uuid = r.getUuid();
        Object searchKey = getKeyForNotExistedResume(uuid);
        updateResume(r, searchKey);
        writeConfirmationToConsole("UPDATED", uuid);
    }

    private Object getKeyForNotExistedResume(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isResumeExist(searchKey)) throw new NotExistStorageException(uuid);
        return searchKey;
    }

    protected abstract Object getSearchKey(String uuid); // @return UUID or INDEX

    protected abstract void updateResume(Resume r, Object searchKey);

    public final void save(Resume r) {
        String uuid = r.getUuid();
        if (isUuidNull(uuid)) return;
        Object searchKey = getKeyForExistedResume(uuid);
        insertResume(r, searchKey);
        writeConfirmationToConsole("SAVED", uuid);
    }

    private boolean isUuidNull(String uuid) {
        if (uuid == null) {
            System.out.println("uuid was not entered. Please try again.");
            return true;
        }
        return false;
    }

    private Object getKeyForExistedResume(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isResumeExist(searchKey)) throw new ExistStorageException(uuid);
        return searchKey;
    }

    protected abstract boolean isResumeExist(Object searchKey);

    protected abstract void insertResume(Resume r, Object searchKey);

    public final Resume get(String uuid) {
        Object searchKey = getKeyForNotExistedResume(uuid);
        writeConfirmationToConsole("EXIST", uuid);
        return getResume(searchKey);
    }

    protected abstract Resume getResume(Object searchKey);

    public final void delete(String uuid) {
        if (isUuidNull(uuid)) return;
        Object searchKey = getKeyForNotExistedResume(uuid);
        removeResume(searchKey);
        writeConfirmationToConsole("DELETED", uuid);
    }

    protected abstract void removeResume(Object searchKey);
}
