package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

/**
 * HashMap based storage for Resumes
 */
public class MapUuidStorage extends AbstractStorage<String> {
    /**
     * HashMap realisation with key map - as uuid, searchKey - as uuid
     */
    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
        super.clear();
    }

    @Override
    protected void updateResume(Resume r, String uuid) {
        map.replace(uuid, r);
    }

    @Override
    protected void insertResume(Resume r, String uuid) {
        map.put(uuid, r);
    }

    @Override
    protected Resume getResume(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void removeResume(String uuid) {
        map.remove(uuid);
    }

    /**
     * @return UUID
     */
    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(String uuid) {
        return map.containsKey(uuid);
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
