package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] list = directory.listFiles();
        if (list == null) return;
        for (File file : list) {
            removeResume(file);
        }
    }

    @Override
    public int size() {
        return (int) Arrays.stream(Objects.requireNonNull(directory.listFiles())).filter(File::isFile).count();
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateResume(Resume r, File file) {
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected boolean isResumeExist(File file) {
        return file.exists();
    }

    @Override
    protected void insertResume(Resume r, File file) {
        try {
            file.createNewFile();
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    protected Resume getResume(File file) {
        if (!file.canRead()) {
            throw new StorageException("IO error", file.getName());
        }
        return doRead(file);
    }

    protected abstract Resume doRead(File file);

    @Override
    protected void removeResume(File file) {
        if (file.isFile() && file.exists()) {
            boolean deleted = file.delete();
            if (!deleted) {
                System.out.println("File can not be deleted: " + file.getAbsolutePath());
            }
        }
    }

    @Override
    protected List<Resume> getCopyAll() {
        return null;
    }
}