package com.urise.webapp.storage;

import com.urise.webapp.serializers.ObjectIOSerializer;

public class ObjectFileStorageTest extends AbstractStorageTest{
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectIOSerializer()));
    }
}