package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveToArray(Resume resume, int index) {
        /* saves resume at sorted storage with prior preparation of storage
        * http://codereview.stackexchange.com/questions/36221/binary-search-for-inserting-in-array#answer-36239 */
        index = -index - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    protected void deleteFromArray(int index) {
        /* deletes found resume at (index), by moving resumes to the left starting from (index + 1) */
        System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
    }
}