package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10_000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("ОК. База резюме очищена.");
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        /* если такое резюме есть в базе, то обновляем его */
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("ОК UPDATE. Резюме '" + uuid + "' обновлено.");
        } else {
            System.out.println("ERROR UPDATE: Резюме '" + uuid + "' в базе нет.");
        }
    }

    public void save(Resume r) {
        String uuid = r.getUuid();
        if (uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: save <uuid>");
            return;
        }
        int index = findIndex(uuid);
        if (size == storage.length) {
            System.out.println("ERROR. Сохранение невозможно. База резюме заполнена.");
        } else if (index < 0) {
            /* если введенный uuid уникален, то сохраняем резюме в базу */
            storage[size] = r;
            size++;
            System.out.println("OK SAVE. Резюме '" + uuid + "' сохраненно.");
        } else {
            System.out.println("ERROR SAVE. Резюме с таким uuid '" + uuid + "' уже имеется в базе.");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            System.out.println("OK GET. Резюме с таким uuid '" + uuid + "' имеется в базе.");
            return storage[index];
        }
        System.out.println("ERROR GET. Резюме с таким uuid '" + uuid + "' нет в базе.");
        return null;
    }

    public void delete(String uuid) {
        /* если uuid не был введен в команде, то выходим из метода */
        if (uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: delete <uuid>");
        } else {/* удаляем резюме, если есть совпадения uuid */
            int index = findIndex(uuid);
            if (index >= 0) {
                storage[index] = storage[size - 1];
                storage[size - 1] = null;
                size--;
                System.out.println("OK DELETE. Резюме '" + uuid + "' удалено");
            } else {
                System.out.println("ERROR DELETE. Резюме с таким uuid '" + uuid + "' нет в базе.");
            }
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
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                index = i;
                break;
            }
        }
        return index;
    }
}
