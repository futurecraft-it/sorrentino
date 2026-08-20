package it.futurecraft.sorrentino.utils.wrappers;

public interface Wrapper {
    <T> T unwrap(Class<T> clazz);
}
