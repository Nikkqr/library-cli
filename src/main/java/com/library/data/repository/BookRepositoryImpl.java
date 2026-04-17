package com.library.data.repository;

import com.library.data.entity.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl implements BookRepository{

    private final HashMap<Integer, Book> storage = new HashMap<>();

    int curId = 1;

    public BookRepositoryImpl() {}

    @Override
    public void save(Book book) {
        if (book.getId() == 0) {
            book.setId(curId++);
        }

        storage.put(curId, book);
    }

    @Override
    public Optional<Book> findById(int id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Book> deleteById(int id) {
        Book removed = storage.remove(id);
        return Optional.ofNullable(removed);
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Optional<Book> findByTitleAndAuthor(String title, String author) {
        return storage.values().stream()
                .filter(x ->  x.getTitle().equalsIgnoreCase(title) && x.getAuthor().equalsIgnoreCase(author))
                .findFirst();
    }
}
