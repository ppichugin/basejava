package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.serializers.SerializerIOStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    /**
     * Context for Pattern Strategy based on Path
     */

    private final Path directory;
    private final SerializerIOStrategy serializer;

    protected PathStorage(String dir, SerializerIOStrategy serializer) {
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
        this.serializer = serializer;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return (new File(String.valueOf(directory), uuid)).toPath();
    }

    @Override
    protected void doUpdate(Resume r, Path file) {
        try {
            serializer.doWrite(r, new BufferedOutputStream(new FileOutputStream(file.toFile())));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return file.toAbsolutePath().toFile().exists();
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            file.toAbsolutePath().toFile().createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.toAbsolutePath(), file.getFileName().toString(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return serializer.doRead(new BufferedInputStream(new FileInputStream(file.toFile())));
        } catch (IOException e) {
            throw new StorageException("Path read error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        if (!file.toFile().delete()) {
            throw new StorageException("Path delete error", file.getFileName().toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Path> files;
        try (Stream<Path> streamPath = Files.walk(Paths.get(String.valueOf(directory)))
                .filter(Files::isRegularFile)) {
            files = streamPath.collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(files.size());
        for (Path file : files) {
            list.add(doGet(file));
        }
        return list;
    }
}