package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectIOSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectIOSerializer()));
    }
}