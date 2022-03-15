package com.urise.webapp;

import java.io.File;
import java.util.Arrays;

public class MainDirectoryList {
    static int files;
    static int folders;
    static int count;

    public static void main(String[] args) {
        final String pathname = "./src";
        listFilesDeeply(new File(pathname));
        System.out.println("Files: " + files + "\nFolders: " + folders);
    }

    private static void listFilesDeeply(File dir) {
        File[] list = dir.listFiles();
        if (list == null) return;
        Arrays.sort(list, (File o1, File o2) -> {
            if (o2.isDirectory()) return 1;
            if (o1.isDirectory()) return -1;
            return 0;
        });
        for (File file : list) {
            if (file.isDirectory()) {
                folders++;
                count++;
                System.out.println(getSpace(count) + "/" + file.getName());
                listFilesDeeply(file);
            }
            if (file.isFile()) {
                files++;
                System.out.println(getSpace(count) + "-" + file.getName());
            }
        }
        count--;
    }

    private static StringBuilder getSpace(int length) {
        StringBuilder str = new StringBuilder();
        str.append("|").append("-".repeat(Math.max(1, length)));
        return str;
    }
}