package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * HashMap based storage for Resumes
 */
public class MapResumeStorage extends AbstractStorage<Resume> {
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
    protected void doUpdate(Resume r, Resume resume) {
        /* 'resume' contains found resume in HashMap that getSearchKey returned */
        map.replace((resume).getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Resume resume) {
        /* 'resume' is empty. Inserting new element doesn't exist in Map yet */
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doDelete(Resume resume) {
        map.remove((resume).getUuid());
    }

    /**
     * @return Resume
     */
    @Override
    protected Resume getSearchKey(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected boolean isExist(Resume resume) {
        return Objects.nonNull(resume);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
