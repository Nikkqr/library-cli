package com.library.presentation;

import com.library.application.BookService;

import java.util.Scanner;

public class Console {

    private final BookService service;

    private final Scanner scanner = new Scanner(System.in);

    public Console(BookService bookService) {
        service = bookService;
    }

    public void start() {
        while (true) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split(" ");
            String command = parts[0].toUpperCase();

            switch (command) {
                case "ADD" -> {
                    String[] params = line.substring(4).trim().split(";");
                    if (params.length != 3) {
                        System.out.println("Неверный формат");
                        continue;
                    }

                    System.out.println(service.addBook(params[0], params[1], params[2]));
                }

                case "REMOVE" -> {
                    if (parts.length != 2) {
                        System.out.println("Неверный формат");
                        continue;
                    }

                    System.out.println(service.removeBook(parts[0]));
                }

                case "LIST" -> {
                    String sortBy = parts.length > 1 ? parts[1] : null;
                    String result = service.getAllBooks(sortBy);
                    System.out.println(result);
                }

                case "FIND" -> {
                    String query = line.substring(5).trim();
                    String result = service.findBooks(query);
                    System.out.println(result);
                }

                case "STATS" -> {
                    String result = service.getStats();
                    System.out.println(result);
                }

                case "EXIT" -> {
                    System.out.println("До встречи!");
                    return;
                }

                default -> System.out.println("Неизвестная команда");
            }

        }
    }
}
