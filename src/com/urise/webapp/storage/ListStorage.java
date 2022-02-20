package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
        ((ArrayList<Resume>)list).trimToSize();
        super.clear();
    }

    @Override
    protected void updateElement(Resume r, int index) {
        list.set(index, r);
    }

    @Override
    protected void insertElement(Resume r, int index) {
        list.add(r);
    }

    @Override
    protected Resume getElement(int index) {
        return list.get(index);
    }

    @Override
    protected void removeElement(int index) {
        list.remove(index);
        ((ArrayList<Resume>)list).trimToSize();
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return list.size();
    }

    protected int getIndex(String uuid) {
        return list.indexOf(new Resume(uuid));
    }
}
