package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance == null) {
            instance = new TestSingleton();
        }
        return instance;
    }

    private TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();

        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.ordinal());

        for (SectionType type : SectionType.values()) {
            System.out.println(type + ": " + type.getTitle());
        }
        System.out.println();

        for (ContactType type : ContactType.values()) {
            System.out.println(type + ": " + type.getTitle());
        }
    }

    public enum Singleton {
        INSTANCE
    }
}
