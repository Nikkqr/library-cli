package com.library.application;

import com.library.data.entity.Book;
import com.library.data.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository mockRepository;

    @InjectMocks
    private BookServiceImpl service;

    @Test
    public void addBook_shouldReturnError_whenDuplicate() {
        when(mockRepository.findByTitleAndAuthor("Car", "Bob"))
                .thenReturn(Optional.of(new Book("Car", "Bob", 2000)));

        String res = service.addBook("Car", "Bob", "2000");

        assertEquals("Ошибка: книга уже существует\n", res);
        verify(mockRepository, never()).save(any(Book.class));
    }

    @Test
    public void removeBook_shouldReturnSuccess_whenFound() {
        Book book = new Book("Animals", "Author", 2013);
        book.setId(2);
        when(mockRepository.deleteById(2)).thenReturn(Optional.of(book));

        String res = service.removeBook("2");

        assertTrue(res.contains("Книга c ID: 2 успешно удалена\n"));
    }

    @Test
    public void removeBook_shouldReturnError_whenNotFound() {
        when(mockRepository.deleteById(2)).thenReturn(Optional.empty());

        String res = service.removeBook("2");

        assertEquals("Ошибка: книга с ID 2 не найдена\n", res);
    }
}
