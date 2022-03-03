package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
        ((ArrayList<Resume>) list).trimToSize();
        super.clear();
    }

    @Override
    protected void updateResume(Resume r, Object index) {
        list.set((Integer) index, r);
    }

    @Override
    protected void insertResume(Resume r, Object searchKey) {
        list.add(r);
    }

    @Override
    protected Resume getResume(Object index) {
        return list.get((Integer) index);
    }

    @Override
    protected void removeResume(Object index) {
        list.remove((int) (Integer) index);
    }

    /* @return INDEX */
    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isResumeExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected List<Resume> getCopyAll() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }
}
