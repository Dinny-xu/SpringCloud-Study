package com.study.userservice;

public class Box<T> {

    private T box;

    public T getFirst() {
        return box;
    }

    void setFirst(T t) {
        this.box = t;
    }

}
