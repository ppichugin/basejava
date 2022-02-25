package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    default void clear() {
        System.out.println("OK. Storage of resume cleared.");
    }

    default void update(Resume r) {
        System.out.println("OK UPDATE. Resume '" + r.getUuid() + "' updated.");
    }

    default void save(Resume r) {
        System.out.println("OK SAVE. Resume '" + r.getUuid() + "' saved.");
    }

    Resume get(String uuid);

    default void delete(String uuid) {
        System.out.println("OK DELETE. Resume '" + uuid + "' deleted.");
    }

    List<Resume> getAllSorted();

    int size();
}