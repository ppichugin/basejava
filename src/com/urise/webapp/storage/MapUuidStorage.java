package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

/**
 * HashMap based storage for Resumes
 */
public class MapUuidStorage extends AbstractStorage {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
        super.clear();
    }

    @Override
    protected void updateResume(Resume r, Object uuid) {
        map.replace((String) uuid, r);
    }

    @Override
    protected void insertResume(Resume r, Object uuid) {
        map.put((String) uuid, r);
    }

    @Override
    protected Resume getResume(Object uuid) {
        return map.get((String) uuid);
    }

    @Override
    protected void removeResume(Object uuid) {
        map.remove((String) uuid);
    }

    @Override // @return UUID
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isResumeExist(Object uuid) {
        return map.containsKey((String) uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(map.values());
        list.sort(COMPARATOR_FULLNAME_THEN_UUID);
        return list;
    }

    @Override
    public int size() {
        return map.size();
    }
}
