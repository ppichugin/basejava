package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;

/**
 * List based storage for Resumes
 */
public class ListStorage extends AbstractStorage {

    protected final ArrayList<Resume> list;

    public ListStorage() {
        list = new ArrayList<>();
    }

    @Override
    protected void clearStorageSpecific() {
        list.clear();
        list.trimToSize();
    }

    @Override
    protected void updateStorageSpecific(int index, Resume r) {
        list.set(index, r);
    }

    @Override
    protected void overflowStorageCheckup(int index, String uuid) {
        // ArrayList is conditionally unlimited
    }

    @Override
    protected void saveStorageSpecific(int index, Resume r) {
        list.add(r);
    }

    @Override
    protected Resume getStorageSpecific(int index) {
        return list.get(index);
    }

    @Override
    protected void deleteStorageSpecific(int index) {
        list.remove(index);
        list.trimToSize();
    }

    @Override
    protected Resume[] getAllStorageSpecific() {
        return list.toArray(new Resume[0]);
    }

    @Override
    protected int sizeStorageSpecific() {
        return list.size();
    }

    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return list.indexOf(searchKey);
    }
}
