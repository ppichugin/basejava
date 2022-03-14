package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * ArrayList based storage for Resumes
 */
public class ListStorage extends AbstractStorage<Integer> {

    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
        ((ArrayList<Resume>) list).trimToSize();
        super.clear();
    }

    @Override
    protected void doUpdate(Resume r, Integer index) {
        list.set(index, r);
    }

    @Override
    protected void doSave(Resume r, Integer searchKey) {
        list.add(r);
    }

    @Override
    protected Resume doGet(Integer index) {
        return list.get(index);
    }

    @Override
    protected void doDelete(Integer index) {
        list.remove(index.intValue());
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
    protected boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(list);
    }

    @Override
    public int size() {
        return list.size();
    }
}
