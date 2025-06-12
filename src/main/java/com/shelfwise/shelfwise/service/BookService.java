package com.shelfwise.shelfwise.service;

import com.shelfwise.shelfwise.dto.BookDto;
import com.shelfwise.shelfwise.dto.BookSimplifyDto;
import com.shelfwise.shelfwise.entity.BookEntity;
import com.shelfwise.shelfwise.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<BookSimplifyDto> getAllBooks() {
        List<BookSimplifyDto> books = bookRepository.findAll().stream().map(b -> {
            BookSimplifyDto book = new BookSimplifyDto();
            book.setId(b.getBid());
            book.setTitle(b.getTitle());
            book.setAuthor(b.getAuthor());
            book.setGenre(b.getGenre());
            book.setPrice(b.getPrice());
            return book;
        }).toList();

        return books;
    }

    public BookDto getBook(UUID id) {
        BookEntity bookEntity = bookRepository.findBookEntitiesByBid(id);

        BookDto book = new BookDto();
        book.setId(bookEntity.getBid());
        book.setTitle(bookEntity.getTitle());
        book.setAuthor(bookEntity.getAuthor());
        book.setGenre(bookEntity.getGenre());
        book.setPrice(bookEntity.getPrice());
        book.setDescription(bookEntity.getDescription());

        return book;
    }

    public List<BookSimplifyDto> getFilteredBooks(String title, String author, String genre) {
        List<BookEntity> results;

        if (title != null && author != null && genre != null) {
            results = bookRepository.findBookEntitiesByTitleAndGenreAndAuthorIgnoreCase(title, genre, author);
        } else if (title != null && author != null) {
            results = bookRepository.findBookEntitiesByAuthorAndTitleIgnoreCase(author, title);
        } else if (title != null && genre != null) {
            results = bookRepository.findBookEntitiesByTitleAndGenreIgnoreCase(title, genre);
        } else if (author != null && genre != null) {
            results = bookRepository.findBookEntitiesByAuthorAndGenreIgnoreCase(author, genre);
        } else if (title != null) {
            results = bookRepository.findBookEntityByTitleIgnoreCase(title);
        } else if (author != null) {
            results = bookRepository.findBookEntitiesByAuthorIgnoreCase(author);
        } else if (genre != null) {
            results = bookRepository.findBookEntityByGenreIgnoreCase(genre);
        } else {
            results = bookRepository.findAll(); // no filters
        }

        return results.stream().map(b -> new BookSimplifyDto(
                b.getBid(),
                b.getTitle(),
                b.getAuthor(),
                b.getGenre(),
                b.getPrice()
        )).toList();
    }
}
