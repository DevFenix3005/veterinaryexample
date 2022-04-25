package com.rebirth.veterinaryexample.app.utils;

import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

import java.util.concurrent.atomic.AtomicReference;

public final class UnirestWrapper {

    private static final AtomicReference<UnirestInstance> _CACHE = new AtomicReference<>();
    public static final UnirestInstance INSTANCE = getInstance();

    private UnirestWrapper() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    private static UnirestInstance getInstance() {
        UnirestInstance value = _CACHE.get();
        if (value == null) {
            synchronized (_CACHE) {
                value = _CACHE.get();
                if (value == null) {
                    UnirestInstance actualValue = Unirest.primaryInstance();
                    _CACHE.set(actualValue);
                }
            }
        }
        return value != null ? value : _CACHE.get();
    }

}
