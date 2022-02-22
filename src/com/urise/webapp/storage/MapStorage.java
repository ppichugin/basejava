package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HashMap based storage for Resumes
 */
public class MapStorage extends AbstractStorage {

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
    public Resume[] getAll() {
        List<Resume> list = new ArrayList<>(map.values());
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
