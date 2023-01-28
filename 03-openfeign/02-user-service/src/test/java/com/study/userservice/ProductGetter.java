package com.study.userservice;

import java.util.ArrayList;
import java.util.Random;

public class ProductGetter<T> {

    ArrayList<T> list = new ArrayList<>();

    public void addProduct(T t) {
        list.add(t);
    }

    public T getProduct() {
        return list.get(new Random().nextInt(list.size()));
    }

}
