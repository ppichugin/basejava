package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    protected void saveToStorage(int index, Resume resume) {
        /* resume to be saved to the last free cell at unsorted storage */
        index = size;
        storage[index] = resume;
    }

    @Override
    protected void deleteAtStorage(int index) {
        /* rewrites found resume with the last item from storage */
        storage[index] = storage[size - 1];
        storage[size - 1] = null;
    }
}