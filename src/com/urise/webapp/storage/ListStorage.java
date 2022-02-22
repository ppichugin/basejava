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

    @Override // @return INDEX
    protected Integer getSearchKey(String uuid) {
        return list.indexOf(new Resume(uuid));
    }

    @Override
    protected boolean isResumeExist(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return list.size();
    }
}
