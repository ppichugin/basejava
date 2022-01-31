package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        if (size == storage.length) {
            size--;
        }
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("ОК. База резюме очищена.");
    }

    public void update(Resume resume) {
        // если такое резюме есть в базе, то обновляем его
        int indexOfResumeToBeUpdated = searchResume(resume.getUuid());
        if (indexOfResumeToBeUpdated >= 0) {
            storage[indexOfResumeToBeUpdated] = resume;
            System.out.println("ОК UPDATE. Резюме '" + storage[indexOfResumeToBeUpdated].getUuid() + "' обновлено.");
        } else {
            System.out.println("ERROR UPDATE: Резюме '" + resume.getUuid() + "' в базе нет.");
        }
    }

    public void save(Resume r) {
        if (r.getUuid() == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: save <uuid>");
            return;
        }
        int indexOfFoundResume = searchResume(r.getUuid());
        if (size == storage.length) {
            System.out.println("ERROR. Сохранение невозможно. База резюме заполнена.");
            // если введенный uuid уникален, то сохраняем резюме в базу
        } else if (indexOfFoundResume < 0) {
            storage[size] = r;
            size++;
            System.out.println("OK SAVE. Резюме '" + r.getUuid() + "' сохраненно.");
        } else {
            System.out.println("ERROR SAVE. Резюме с таким uuid '" + r.getUuid() + "' уже имеется в базе.");
        }
    }

    public Resume get(String uuid) {
        int indexOfResumeToBeFound = searchResume(uuid);
        try {
            System.out.println("OK GET. Резюме с таким uuid '" + storage[indexOfResumeToBeFound].getUuid() + "' имеется в базе.");
            return storage[indexOfResumeToBeFound];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR GET. Резюме с таким uuid '" + uuid + "' нет в базе.");
        }
        return null;
    }

    public void delete(String uuid) {
        // если uuid не был введен в команде, то выходим из метода
        if (uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: delete <uuid>");
            return;
        }
        // удаляем резюме, если есть совпадения uuid
        int indexOfResumeToBeDeleted = searchResume(uuid);
        try {
            // если это последний элемент в массиве, то удаляем резюме и выходим из метода
            if (indexOfResumeToBeDeleted == storage.length - 1) {
                storage[indexOfResumeToBeDeleted] = null;
            } else {
                storage[indexOfResumeToBeDeleted] = storage[size - 1];
                storage[size - 1] = null;
            }
            System.out.println("OK DELETE. Резюме '" + uuid + "' удалено");
            size--;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR DELETE. Резюме с таким uuid '" + uuid + "' нет в базе.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public int searchResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
