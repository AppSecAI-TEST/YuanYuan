package xyz.zimuju.common.entity;

import xyz.zimuju.common.base.BaseModel;

public class Entry<K, V> extends BaseModel {
    private static final long serialVersionUID = 1L;

    public K key;
    public V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public Entry() {

    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public boolean isCorrect() {
        return key != null;
    }

}
