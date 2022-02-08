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
    protected int findPlaceToSave(int index) {
        /* returns appropriate index to save at sorted storage */
        index = -index - 1;
        return index;
    }

    @Override
    protected void rearrangeStorageForDeletion(int index) {
        /* move resumes from next to previous starting by index+1 */
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }

    @Override
    protected void prepareStorageToInsert(int index) {
        /* free up space to insert resume in sorted order */
        System.arraycopy(storage, index, storage, index + 1, size - index);
    }
}