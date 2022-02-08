package com.urise.webapp.storage;

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
    protected int findPlaceToSave(int index) {
        /* resume to be saved to the last free cell at unsorted storage */
        index = size;
        return index;
    }

    @Override
    protected void rearrangeStorageForDeletion(int index) {
        /* rewrite found resume with the last item from storage */
        storage[index] = storage[size - 1];
    }

    @Override
    protected void prepareStorageToInsert(int index) {
        /* nothing to-do special with unsorted storage */
    }
}