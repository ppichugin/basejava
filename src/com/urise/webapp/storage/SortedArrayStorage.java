package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
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
            /* если введенный uuid уникален, то сохраняем резюме в базу, сохраняя порядок сортировки массива */
            int indexForInsert = Arrays.binarySearch(storage, 0, size, r);
            if (indexForInsert < 0) {
                indexForInsert = - indexForInsert - 1;
            }
            System.arraycopy(storage, indexForInsert, storage, indexForInsert + 1, size - indexForInsert);
            storage[indexForInsert] = r;
            size++;
            System.out.println("OK SAVE. Резюме '" + uuid + "' сохраненно.");
        } else {
            System.out.println("ERROR SAVE. Резюме с таким uuid '" + uuid + "' уже имеется в базе.");
        }
    }

    @Override
    public void delete(String uuid) {
        /* если uuid не был введен в команде, то выходим из метода */
        if (uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: delete <uuid>");
        } else {/* удаляем резюме, если есть совпадения uuid */
            int index = getIndex(uuid);
            if (index >= 0) {
                System.arraycopy(storage, index + 1, storage, index, size - (index + 1));
                storage[size - 1] = null;
                size--;
                System.out.println("OK DELETE. Резюме '" + uuid + "' удалено");
            } else {
                System.out.println("ERROR DELETE. Резюме с таким uuid '" + uuid + "' нет в базе.");
            }
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
