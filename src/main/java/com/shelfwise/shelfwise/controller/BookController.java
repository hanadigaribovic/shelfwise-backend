package com.shelfwise.shelfwise.controller;

import com.shelfwise.shelfwise.dto.BookDto;
import com.shelfwise.shelfwise.dto.BookSimplifyDto;
import com.shelfwise.shelfwise.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping
    public List<BookSimplifyDto> getBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable UUID id){
        return bookService.getBook(id);
    }

    @GetMapping("/search")
    public List<BookSimplifyDto> getFilteredBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam(required = false) String genre){
        return bookService.getFilteredBooks(title, author, genre);
    }
}
