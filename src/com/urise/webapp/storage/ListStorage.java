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
    protected void updateResume(Resume r, int index) {
        list.set(index, r);
    }

    @Override
    protected void insertResume(Resume r, int index) {
        list.add(r);
    }

    @Override
    protected Resume getResume(int index) {
        return list.get(index);
    }

    @Override
    protected void removeResume(int index) {
        list.remove(index);
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
