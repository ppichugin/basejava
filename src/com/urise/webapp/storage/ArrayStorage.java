package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        String uuid = r.getUuid();
        if (uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: save <uuid>");
            return;
        }
        int index = getIndex(uuid);
        if (size == STORAGE_LIMIT) {
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

    public void delete(String uuid) {
        /* если uuid не был введен в команде, то выходим из метода */
        if (uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: delete <uuid>");
        } else {/* удаляем резюме, если есть совпадения uuid */
            int index = getIndex(uuid);
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
}
