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
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("ОК. База резюме очищена.");
    }

    public void update(Resume resume) {
        /* если такое резюме есть в базе, то обновляем его */
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("ОК UPDATE. Резюме '" + storage[index].getUuid() + "' обновлено.");
        } else {
            System.out.println("ERROR UPDATE: Резюме '" + resume.getUuid() + "' в базе нет.");
        }
    }

    public void save(Resume r) {
        if (r.getUuid() == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: save <uuid>");
            return;
        }
        int index = findIndex(r.getUuid());
        if (size == storage.length) {
            System.out.println("ERROR. Сохранение невозможно. База резюме заполнена.");
        } else if (index < 0) {
            /* если введенный uuid уникален, то сохраняем резюме в базу */
            storage[size] = r;
            size++;
            System.out.println("OK SAVE. Резюме '" + r.getUuid() + "' сохраненно.");
        } else {
            System.out.println("ERROR SAVE. Резюме с таким uuid '" + r.getUuid() + "' уже имеется в базе.");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            System.out.println("OK GET. Резюме с таким uuid '" + storage[index].getUuid() + "' имеется в базе.");
            return storage[index];
        } else {
            System.out.println("ERROR GET. Резюме с таким uuid '" + uuid + "' нет в базе.");
        }
        return null;
    }

    public void delete(String uuid) {
        /* если uuid не был введен в команде, то выходим из метода */
        if (uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: delete <uuid>");
            return;
        }
        /* удаляем резюме, если есть совпадения uuid */
        int index = findIndex(uuid);
        if (index >= 0) {
            if (index == storage.length - 1) {
                storage[index] = null;
            } else {
                storage[index] = storage[size - 1];
                storage[size - 1] = null;
            }
            System.out.println("OK DELETE. Резюме '" + uuid + "' удалено");
            size--;
        } else {
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

    private int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
