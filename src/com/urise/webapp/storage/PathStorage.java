package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.SerializerIOStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
     * Context for Pattern Strategy based on java.nio.file.Path
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
        listFiles().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) listFiles().count();
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path file) {
        try {
            serializer.doWrite(r, new BufferedOutputStream(Files.newOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.toFile().getName(), e);
        }
    }

    @Override
    protected boolean isExist(Path file) {
        return Files.exists(file);
    }

    @Override
    protected void doSave(Resume r, Path file) {
        try {
            Files.createFile(file);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.toAbsolutePath(), file.toFile().getName(), e);
        }
        doUpdate(r, file);
    }

    @Override
    protected Resume doGet(Path file) {
        try {
            return serializer.doRead(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Path read error", file.toFile().getName(), e);
        }
    }

    @Override
    protected void doDelete(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Path delete error", file.toFile().getName(), e);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return listFiles().map(this::doGet).collect(Collectors.toCollection(ArrayList::new));
    }

    private Stream<Path> listFiles() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory error " + directory.toAbsolutePath(), null);
        }
    }
}