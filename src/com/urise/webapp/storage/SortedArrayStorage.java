package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveToStorage(int index, Resume resume) {
        /* saves resume at sorted storage with prior preparation of storage */
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteAtStorage(int index) {
        /* deletes found resume at (index), by moving resumes to the left starting from (index + 1) */
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
        storage[size - 1] = null;
    }
}