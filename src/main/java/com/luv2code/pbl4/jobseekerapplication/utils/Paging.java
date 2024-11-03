package com.luv2code.pbl4.jobseekerapplication.utils;

import java.util.ArrayList;
import java.util.List;


public class Paging<T> {
    private static Paging<?> instance;

    @SuppressWarnings("unchecked")
    public static <T> Paging<T> getInstance() {
        if (instance == null) {
            instance = new Paging<>();
        }
        return (Paging<T>) instance;
    }

    public List<T> getListByPage(List<T> list, int start, int end) {
        ArrayList<T> arr = new ArrayList<>();
        for (int i = start; i < end; i++) {
            arr.add(list.get(i));
        }
        return arr;
    }
}