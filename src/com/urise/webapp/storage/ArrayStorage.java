package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array (not sorted)
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override // @return INDEX
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveToArray(Resume resume, int index) {
        /* resume to be saved to the last free cell at unsorted storage */
        storage[size] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        /* rewrites found resume with the last item from storage */
        storage[index] = storage[size - 1];
    }
}