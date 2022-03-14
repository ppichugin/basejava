package com.urise.webapp;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainDirectoryList {
    static List<File> filesAndFolders = new ArrayList<>();
    static int files;
    static int folders;

    public static void main(String[] args) {
        final String pathname = "./src";
        listFiles(new File(pathname));
        System.out.println("Files: \n" + filesAndFolders.stream()
                .sorted(Comparator.comparing(File::getPath))
                .map(File::getName)
                .collect(Collectors.joining("\n")));
        System.out.println("Files: " + files + "\nFolders: " + folders);
    }

    private static void listFiles(File dir) {
        if (!dir.isDirectory()) return;
        File[] list = dir.listFiles();
        if (list != null) {
            for (File name : list) {
                if (name.isDirectory()) {
                    folders++;
                    listFiles(name);
                }
                if (name.isFile()) {
                    files++;
                    filesAndFolders.add(name);
                }
            }
        }
    }
}