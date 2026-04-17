package com.library.data.repository;

import com.library.data.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    void save(Book book);

    Optional<Book> findById(int id);

    Optional<Book> deleteById(int id);

    Optional<Book> findByTitleAndAuthor(String title, String author);

    List<Book> findAll();
}
