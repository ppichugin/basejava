/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];;
    private int size;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
        System.out.println("База резюме очищена.");
    }

    void save(Resume r) {
        if (r.uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: save <uuid>");
            return;
        }
        if (size == storage.length) {
            System.out.println("Сохранение невозможно. База резюме заполнена.");
        // если введенный uuid уникален, то сохраняем резюме в базу
        } else if (get(r.uuid) == null) {
            storage[size] = r;
            size++;
            System.out.println("Резюме '" + r.uuid + "' сохраненно.");
        } else {
            System.out.println("Резюме с таким uuid '" + r.uuid + "' уже имеется в базе.");
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        // если uuid не был введен в команде, то выходим из метода
        if (uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: delete <uuid>");
            return;
        }
        // удаляем резюме, если есть совпадения uuid
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].uuid)) {
                // если это последний элемент в массиве, то удаляем резюме и выходим из метода
                if (i == storage.length - 1) {
                    storage[i] = null;
                } else {
                    // перемещаем ячейки внутри массива со сдвигом влево
                    System.arraycopy(storage, i + 1, storage, i, size - (i + 1));
                    // обнуляем последнюю ячейку диапазона как дубликат
                    storage[size - 1] = null;
                }
                System.out.println("Резюме '" + uuid + "' удалено");
                size--;
                return;
            }
        }
        System.out.println("Резюме с таким uuid '" + uuid + "' нет в базе.");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    int size() {
        return size;
    }
}
