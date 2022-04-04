package com.urise.webapp;

import com.urise.webapp.model.Resume;

public class MainDeadLock {
    public static void main(String[] args) {
        final Resume r1 = ResumeTestData.createResume("uuid1", "Ivanov");
        final Resume r2 = ResumeTestData.createResume("uuid2", "Petrov");

        threadRequest(r1, r2);
        threadRequest(r2, r1);
    }

    private static void threadRequest(Resume r1, Resume r2) {
        new Thread(() -> {
            synchronized (r1) {
                printThreadStatus("Захват объекта: ", r1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                printThreadStatus("попытка захватить объект: ", r2);
                synchronized (r2) {
                    printThreadStatus("объект захвачен: ", r2);
                }
            }
        }).start();
    }

    private static void printThreadStatus(String x, Resume resume) {
        System.out.println(Thread.currentThread().getName() + ": " + x + resume.getUuid());
    }
}