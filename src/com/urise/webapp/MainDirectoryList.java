package com.urise.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainDirectoryList {
    static List<String> filesAndFolders = new ArrayList<>();
    static int files;
    static int folders;

    public static void main(String[] args) {
        final String pathname = "C:/MyJava/basejava/src";
        DirectoryList(new File(pathname));
        System.out.println(filesAndFolders.stream()
                .sorted(Comparator.naturalOrder())
                .map(String::toString)
                .collect(Collectors.joining("\n")));
        System.out.println("Files: " + files + "\nFolders: " + folders);
    }

    private static void DirectoryList(File dir) {
        if (!dir.isDirectory()) return;
        File[] list = dir.listFiles();
        if (list != null) {
            for (File name : list) {
                if (name.isDirectory()) {
                    folders++;
                    DirectoryList(name);
                }
                if (name.isFile()) {
                    files++;
                }
                filesAndFolders.add(name.getAbsolutePath());
            }
        }
    }
}