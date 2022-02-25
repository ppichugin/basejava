package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HashMap based storage for Resumes
 */
public class MapResumeStorage extends AbstractStorage {

    private final Map<Resume, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
        super.clear();
    }

    @Override
    protected void updateResume(Resume r, Object resume) {
        map.replace(r, r);
    }

    @Override
    protected void insertResume(Resume r, Object resume) {
        map.put(r, r);
    }

    @Override
    protected Resume getResume(Object resume) {
        return map.get((Resume) resume);
    }

    @Override
    protected void removeResume(Object resume) {
        map.remove((Resume) resume);
    }

    @Override // @return Resume
    protected Resume getSearchKey(String uuid) {
        Resume resume = null;
        for (Map.Entry<Resume, Resume> entry : map.entrySet()) {
            if (entry.getKey().getUuid().equals(uuid)) {
                resume = entry.getValue();
            }
        }
        return resume;
    }

    @Override
    protected boolean isResumeExist(Object resume) {
        return map.containsKey((Resume) resume);
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
