package com.library;

import com.library.application.BookServiceImpl;
import com.library.data.repository.BookRepositoryImpl;
import com.library.presentation.Console;

public class Main {
    public static void main(String[] args){
        Console c = new Console(new BookServiceImpl(new BookRepositoryImpl()));
        c.start();
    }
}
