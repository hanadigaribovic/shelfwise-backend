package com.shelfwise.shelfwise.service;

import com.shelfwise.shelfwise.dto.BookDto;
import com.shelfwise.shelfwise.dto.BookSimplifyDto;
import com.shelfwise.shelfwise.entity.BookEntity;
import com.shelfwise.shelfwise.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private UUID bookId;
    private BookEntity bookEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookId = UUID.randomUUID();
        bookEntity = new BookEntity(bookId, "Title", "Author", "Genre", 19.99, "Desc");
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));

        List<BookSimplifyDto> result = bookService.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Title", result.get(0).getTitle());
    }

    @Test
    void testGetBook() {
        when(bookRepository.findBookEntitiesByBid(bookId)).thenReturn(bookEntity);

        BookDto dto = bookService.getBook(bookId);

        assertEquals(bookId, dto.getId());
        assertEquals("Title", dto.getTitle());
    }

    @Test
    void testGetFilteredBooks_TitleOnly() {
        when(bookRepository.findBookEntityByTitleIgnoreCase("Title")).thenReturn(List.of(bookEntity));

        List<BookSimplifyDto> result = bookService.getFilteredBooks("Title", null, null);

        assertEquals(1, result.size());
    }

    @Test
    void testGetFilteredBooks_AuthorOnly() {
        when(bookRepository.findBookEntitiesByAuthorIgnoreCase("Author")).thenReturn(List.of(bookEntity));

        List<BookSimplifyDto> result = bookService.getFilteredBooks(null, "Author", null);

        assertEquals(1, result.size());
    }

    @Test
    void testGetFilteredBooks_GenreOnly() {
        when(bookRepository.findBookEntityByGenreIgnoreCase("Genre")).thenReturn(List.of(bookEntity));

        List<BookSimplifyDto> result = bookService.getFilteredBooks(null, null, "Genre");

        assertEquals(1, result.size());
    }

    @Test
    void testGetFilteredBooks_TitleAndAuthor() {
        when(bookRepository.findBookEntitiesByAuthorAndTitleIgnoreCase("Author", "Title"))
                .thenReturn(List.of(bookEntity));

        List<BookSimplifyDto> result = bookService.getFilteredBooks("Title", "Author", null);

        assertEquals(1, result.size());
    }

    @Test
    void testGetFilteredBooks_TitleAndGenre() {
        when(bookRepository.findBookEntitiesByTitleAndGenreIgnoreCase("Title", "Genre"))
                .thenReturn(List.of(bookEntity));

        List<BookSimplifyDto> result = bookService.getFilteredBooks("Title", null, "Genre");

        assertEquals(1, result.size());
    }

    @Test
    void testGetFilteredBooks_AuthorAndGenre() {
        when(bookRepository.findBookEntitiesByAuthorAndGenreIgnoreCase("Author", "Genre"))
                .thenReturn(List.of(bookEntity));

        List<BookSimplifyDto> result = bookService.getFilteredBooks(null, "Author", "Genre");

        assertEquals(1, result.size());
    }

    @Test
    void testGetFilteredBooks_TitleAuthorGenre() {
        when(bookRepository.findBookEntitiesByTitleAndGenreAndAuthorIgnoreCase("Title", "Genre", "Author"))
                .thenReturn(List.of(bookEntity));

        List<BookSimplifyDto> result = bookService.getFilteredBooks("Title", "Author", "Genre");

        assertEquals(1, result.size());
    }

    @Test
    void testGetFilteredBooks_AllNull() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(bookEntity));

        List<BookSimplifyDto> result = bookService.getFilteredBooks(null, null, null);

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAll();
    }
}
