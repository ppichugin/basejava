package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> map = new HashMap<>();

    @Override
    public void clear() {
        map.clear();
        super.clear();
    }

    @Override
    protected void updateResume(Resume r, int index) {
        map.replace(r.getUuid(), r);
    }

    @Override
    protected int getIndex(String uuid) {
        if (map.containsKey(uuid)) return 0;
        return -1;
    }

    @Override
    protected void insertResume(Resume r, int index) {
        map.put(r.getUuid(), r);
    }

    @Override
    protected Resume getResume(int index, String uuid) {
        return map.get(uuid);
    }

    @Override
    protected void removeResume(int index, String uuid) {
        map.remove(uuid);
    }

    @Override
    public Resume[] getAll() {
        List<Resume> list = new ArrayList<>(map.values());
        Collections.sort(list);
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }
}
