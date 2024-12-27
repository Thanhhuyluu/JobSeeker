package com.luv2code.pbl4.jobseekerapplication.dto;

import java.util.List;

public class ListResult <T>{
    private List<T> items;
    private Long totalItems;

    public ListResult() {
    }

    public ListResult(List<T> items, Long totalItems) {
        this.items = items;
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Long getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Long totalItems) {
        this.totalItems = totalItems;
    }
    public int getTotalPages(int pageSize) {
        return (int) Math.ceil((double) totalItems / pageSize);
    }
}
