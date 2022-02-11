package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println(field.getName());
        System.out.println(field.get(r));
        field.set(r, "new_uuid");
        System.out.println(r);

        // TODO : invoke r.toString via reflection
        System.out.println("Reflection method toString for existing resume ===== ");
        Method methodToString2 = r.getClass().getDeclaredMethod("toString");
        System.out.println("Invoke method " + methodToString2.getName() + " for existing Resume: " + methodToString2.invoke(r));

        System.out.println("\nReflection method toString for new resume ===== ");
        Method methodToString = Resume.class.getMethod("toString");
        System.out.println("Invoke method " + methodToString.getName() + " for new Resume: " + methodToString.invoke(new Resume()));
    }
}
