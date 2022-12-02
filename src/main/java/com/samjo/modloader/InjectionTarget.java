package com.samjo.modloader;

public abstract class InjectionTarget<T> {
    public abstract T run(Object[] args);
}
