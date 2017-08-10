package xyz.zimuju.common.cache;

/**
 */
public interface Cache {
    String get(final String key);

    void put(final String key, final String value);

    boolean remove(final String key);
}
