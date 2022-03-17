package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.ObjectIOSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectIOSerializer()));
    }
}