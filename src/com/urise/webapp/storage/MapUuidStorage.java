package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    protected void doUpdate(Resume r, String uuid) {
        map.replace(uuid, r);
    }

    @Override
    protected void doSave(Resume r, String uuid) {
        map.put(uuid, r);
    }

    @Override
    protected Resume doGet(String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
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
    protected boolean isExist(String uuid) {
        return map.containsKey(uuid);
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
