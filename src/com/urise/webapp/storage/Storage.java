package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid);

    void delete(String uuid);

    Resume[] getAll();

    int size();

    default void clearedOk() {
        System.out.println("OK. Storage of resume cleared.");
    }

    default void updatedOk(String uuid) {
        System.out.println("OK UPDATE. Resume '" + uuid + "' updated.");
    }

    default void savedOk(String uuid) {
        System.out.println("OK SAVE. Resume '" + uuid + "' saved.");
    }

    default void exists(String uuid) {
        System.out.println("OK GET. Resume '" + uuid + "' exists.");
    }

    default void deletedOk(String uuid) {
        System.out.println("OK DELETE. Resume '" + uuid + "' deleted.");
    }
}