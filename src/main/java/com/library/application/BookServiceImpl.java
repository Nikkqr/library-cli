package com.library.application;

import com.library.data.entity.Book;
import com.library.data.repository.BookRepository;

import java.util.*;

public class BookServiceImpl implements BookService{

    private final BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public String addBook(String title, String author, String year) {
        try {
            int numYear = Integer.parseInt(year);

            if (repository.findByTitleAndAuthor(title, author).isPresent()) {
                return "Ошибка: книга уже существует\n";
            }

            Book book = new Book(title, author, numYear);
            repository.save(book);
            return "Книга добавлена (ID: " + book.getId() + ")\n";
        }
        catch (NumberFormatException e) {
            return "Ошибка: год должен быть числом\n";
        }

    }

    @Override
    public String removeBook(String id) {
        try {
            int numId = Integer.parseInt(id);
            Optional<Book> removedBook = repository.deleteById(numId);
            if (removedBook.isEmpty()) {
                return "Ошибка: книга с ID " + id + " не найдена\n";
            }

            return "Книга c ID: " + id + " успешно удалена\n";
        }
        catch (NumberFormatException e) {
            return "Ошибка: id должен быть числом\n";
        }
    }

    @Override
    public String findBooks(String query) {
        List<Book> found = repository.findAll().stream()
                .filter(b -> b.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        b.getAuthor().toLowerCase().contains(query.toLowerCase()))
                .toList();

        if (found.isEmpty()) {
            return "Ошибка: книг не найдено\n";
        }

        StringBuilder sb = new StringBuilder("Найдено книг: " + found.size() + "\n");
        for (Book book : found) {
            sb.append(String.format("%d. \"%s\" - %s (%d)\n",
                    book.getId(), book.getTitle(), book.getAuthor(), book.getYear()));
        }

        return sb.toString();
    }

    @Override
    public String getAllBooks(String sortBy) {
        List<Book> books = repository.findAll();
        if (books.isEmpty()) {
            return "Библиотека пуста\n";
        }

        if ("title".equalsIgnoreCase(sortBy)) {
            books.sort(Comparator.comparing(Book::getTitle));
        }
        else if ("author".equalsIgnoreCase(sortBy)) {
            books.sort(Comparator.comparing(Book::getAuthor));
        }
        else if ("year".equalsIgnoreCase(sortBy)) {
            books.sort(Comparator.comparing(Book::getYear));
        }

        StringBuilder sb = new StringBuilder();
        for (Book book : books) {
            sb.append(String.format("%d. \"%s\" - %s (%d)\n",
                    book.getId(), book.getTitle(), book.getAuthor(), book.getYear()));
        }

        return sb.toString();
    }

    @Override
    public String getStats() {
        List<Book> books = repository.findAll();

        if (books.isEmpty()) {
            return "Библиотека пуста\n";
        }

        int total = books.size();
        Book oldest = books.stream().min(Comparator.comparing(Book::getYear)).get();
        Book newest = books.stream().max(Comparator.comparing(Book::getYear)).get();

        Map<String, Long> authorStats = new HashMap<>();
        for (Book book : books) {
            authorStats.merge(book.getAuthor(), 1L, Long::sum);
        }

        List<String> authors = new ArrayList<>(authorStats.keySet());
        authors.sort((a, b) -> authorStats.get(b).compareTo(authorStats.get(a)));
        List<String> topAuthors = authors.subList(0, Math.min(3, authors.size()));

        String template = """
        *Статистика*
        Всего книг: %d
        Самая старая: %s (%d)
        Самая новая: %s (%d)
        
        Топ-3 авторов:
        """;

        StringBuilder sb = new StringBuilder(String.format(template,
                total,
                oldest.getTitle(), oldest.getYear(),
                newest.getTitle(), newest.getYear()
        ));

        for (String name : topAuthors) {
            sb.append(String.format("  %s: %d книг\n", name, authorStats.get(name)));
        }

        return sb.toString();
    }
}
