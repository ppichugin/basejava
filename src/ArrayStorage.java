/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int lastResume = size() - 1;
        for (int i = 0; i <= lastResume; i++) {
            storage[i] = null;
        }
        System.out.println("База резюме очищена.");
    }

    void save(Resume r) {
        if (r.uuid == null) {
            System.out.println("Вы не ввели uuid. Повторите ввод команды в формате: save <uuid>");
            return;
        }
        int indexOfEmptyRecord = size();
        if (indexOfEmptyRecord == storage.length) {
            System.out.println("Сохранение невозможно. База резюме заполнена.");
        } else {
            // если введенный uuid уникален, то сохраняем резюме в базу
            if (get(r.uuid) == null) {
                storage[indexOfEmptyRecord] = r;
                System.out.println("Резюме '" + r.uuid + "' сохраненно.");
            } else {
                System.out.println("Резюме с таким uuid '" + r.uuid + "' уже имеется в базе.");
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size(); i++) {
            Resume resumeToBeFound = storage[i];
            if (uuid.equals(resumeToBeFound.uuid)) {
                return resumeToBeFound;
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
        boolean isFound = false;
        int quantityOfAllResume = size();
        // удаляем резюме, если есть совпадения uuid
        for (int i = 0; i < quantityOfAllResume; i++) {
            if (uuid.equals(storage[i].uuid)) {
                isFound = true;
                storage[i] = null;
                System.out.println("Резюме '" + uuid + "' удалено");
                // если это последний элемент в массиве, то выходим из метода
                if (i == storage.length - 1) {
                    return;
                }
                // копируем часть массива после удаленного элемента во временный массив
                Resume[] tempResume = new Resume[quantityOfAllResume - (i + 1)];
                System.arraycopy(storage, i + 1, tempResume, 0, quantityOfAllResume - (i + 1));
                // (вар.1)удаляем последнее резюме исходного массива в диапазоне скопированного фрагмента как дубликат, возникающий после сдвига влево
                storage[quantityOfAllResume - 1] = null;
                // (вар.2)очищаем исходный массив, начиная с индекса скопированного элемента (чтобы удалить String ссылки массива на ячейки в куче?)
                // Arrays.fill(storage, i + 1, storage.length - 1, null);
                // копируем временный массив в исходный на одну позицию левее
                System.arraycopy(tempResume, 0, storage, i, tempResume.length);
                break;
            }
        }
        if (!isFound) {
            System.out.println("Резюме с таким uuid '" + uuid + "' нет в базе.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int quantityOfResume = size();
        Resume[] tempResume = new Resume[quantityOfResume];
        System.arraycopy(storage, 0, tempResume, 0, quantityOfResume);
        return tempResume;
    }

    int size() {
        int indexOfFirstEmptyRecord = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                indexOfFirstEmptyRecord = i;
                break;
            }
        }
        return indexOfFirstEmptyRecord;
    }
}
