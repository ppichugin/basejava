package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

/**
 * HashMap based storage for Resumes
 */
public class MapResumeStorage extends AbstractStorage {
    /**
     * HashMap realisation with key map - as uuid, searchKey - as Resume
     */
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
        super.clear();
    }

    @Override
    protected void updateResume(Resume r, Object resume) {
        /* 'Object resume' contains found resume in HashMap that getSearchKey returned */
        map.replace(((Resume) resume).getUuid(), r);
    }

    @Override
    protected void insertResume(Resume r, Object resume) {
        /* 'Object resume' is empty. Inserting new element doesn't exist in Map yet */
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(Object resume) {
        return (Resume) resume;
    }

    @Override
    protected void removeResume(Object resume) {
        map.remove(((Resume) resume).getUuid());
    }

    /**
     * @return Resume
     */
    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isResumeExist(Object resume) {
        return Objects.nonNull(resume);
    }

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
