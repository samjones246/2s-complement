package com.samjo.modloader;

public abstract class InjectionHandler<T> {
    public abstract T run(
        Object[] args,
        InjectionTarget<T> orig
    );
}
