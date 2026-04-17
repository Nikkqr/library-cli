package com.library.data.entity;

import lombok.Data;

@Data
public class Book {

    private int id;

    private final String title;

    private final String author;

    private final int year;

    public Book(String title, String author, int year) {
        id = 0;
        this.title = title;
        this.author = author;
        this.year = year;
    }
}
