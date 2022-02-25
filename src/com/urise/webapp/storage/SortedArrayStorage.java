package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Sorted Array
 */
public class SortedArrayStorage extends AbstractArrayStorage {
    /*
    private static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
    */

    private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override // @return INDEX
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, size, searchKey, RESUME_COMPARATOR);
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