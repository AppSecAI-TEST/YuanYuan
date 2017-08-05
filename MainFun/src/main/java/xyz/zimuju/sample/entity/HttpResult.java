package xyz.zimuju.sample.entity;

public class HttpResult<T> {

    public boolean error;
    //@SerializedName(value = "results", alternate = {"result"})
    public T results;
}
