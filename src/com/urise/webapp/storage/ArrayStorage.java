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
        // получаем ссылку на ячейку массива
        Resume resumeToBeUpdated = get(resume.getUuid());
        // если такое резюме есть в базе, то обновляем его
        if (resumeToBeUpdated != null) {
            resumeToBeUpdated = resume;
            System.out.println("ОК UPDATE. Резюме '" + resumeToBeUpdated.getUuid() + "' обновлено.");
        } else {
            System.out.println("ERROR UPDATE: Резюме '" + resume.getUuid() + "' в базе нет.");
        }
    }

    public void save(Resume r) {
        if (r.getUuid() == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: save <uuid>");
            return;
        }
        if (size == storage.length) {
            System.out.println("ERROR. Сохранение невозможно. База резюме заполнена.");
            // если введенный uuid уникален, то сохраняем резюме в базу
        } else if (get(r.getUuid()) == null) {
            storage[size] = r;
            size++;
            System.out.println("OK SAVE. Резюме '" + r.getUuid() + "' сохраненно.");
        } else {
            System.out.println("ERROR SAVE. Резюме с таким uuid '" + r.getUuid() + "' уже имеется в базе.");
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return storage[i];
            }
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
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                // если это последний элемент в массиве, то удаляем резюме и выходим из метода
                if (i == storage.length - 1) {
                    storage[i] = null;
                } else {
                    // вар.1 перемещаем ячейки внутри массива со сдвигом влево, если нужно сохранять последовательность
                    //System.arraycopy(storage, i + 1, storage, i, size - (i + 1));
                    // вар.2 перемещаем последнюю ячейку на место удаляемой
                    storage[i] = storage[size - 1];
                    // обнуляем последнюю ячейку диапазона как дубликат
                    storage[size - 1] = null;
                }
                System.out.println("OK DELETE. Резюме '" + uuid + "' удалено");
                size--;
                return;
            }
        }
        System.out.println("ERROR DELETE. Резюме с таким uuid '" + uuid + "' нет в базе.");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        // вар.1 (более быстрый?)
        //System.arraycopy(storage, 0, resumes, 0, size);
        // вар.2
        resumes = Arrays.copyOf(storage, size);
        return resumes;
    }

    public int size() {
        return size;
    }
}
