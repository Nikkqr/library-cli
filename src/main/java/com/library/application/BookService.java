package com.library.application;

import com.library.data.entity.Book;
import com.library.data.repository.BookRepository;

import java.util.List;
import java.util.Optional;

public interface BookService {

    String addBook(String title, String author, String year);

    String removeBook(String id);

    String findBooks(String query);

    String getAllBooks(String sortBy);

    String getStats();
}
