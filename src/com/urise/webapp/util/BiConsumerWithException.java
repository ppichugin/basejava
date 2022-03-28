package com.urise.webapp.util;

import java.io.IOException;

@FunctionalInterface
public interface BiConsumerWithException<T, U> {
    void accept(T t, U u) throws IOException;
}
