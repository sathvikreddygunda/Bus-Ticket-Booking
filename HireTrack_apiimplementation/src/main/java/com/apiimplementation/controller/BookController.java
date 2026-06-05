package com.apiimplementation.controller;


import com.apiimplementation.dto.CreateBookDto;
import com.apiimplementation.model.Book;
import com.apiimplementation.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/{author_id}")
    public Book addBook(@PathVariable Integer author_id,
                        @RequestBody CreateBookDto dto){

        return bookService.addBook(
                author_id,
                dto
        );

    }
}
