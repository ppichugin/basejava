package com.urise.webapp.storage;

import com.urise.webapp.serializers.ObjectIOSerializer;

public class ObjectPathStorageTest extends AbstractStorageTest{
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.toString(), new ObjectIOSerializer()));
    }
}