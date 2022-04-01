package com.urise.webapp;

import com.urise.webapp.model.Resume;

public class MainDeadLockTest {
    public static void main(String[] args) {
        final Resume r1 = ResumeTestData.createResume("uuid1", "Ivanov");
        final Resume r2 = ResumeTestData.createResume("uuid2", "Petrov");

        new Thread(() -> {
            synchronized (r1) {
                printThreadStatus("объект r1 захвачен.");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                printThreadStatus("попытка захватить объект r2.");
                synchronized (r2) {
                    printThreadStatus("объект r2 захвачен.");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (r2) {
                printThreadStatus("объект r2 захвачен.");
                try {
                    printThreadStatus("попытка захватить объект r1.");
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                synchronized (r1) {
                    printThreadStatus("объект r1 захвачен.");
                }
            }
        }).start();
    }

    private static void printThreadStatus(String x) {
        System.out.println(Thread.currentThread().getName() + ": " + x);
    }
}