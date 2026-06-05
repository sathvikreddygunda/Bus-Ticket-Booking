package com.apiimplementation.service;


import com.apiimplementation.dto.CreateBookDto;
import com.apiimplementation.exception.ResourceNotFoundException;
import com.apiimplementation.model.Author;
import com.apiimplementation.model.Book;
import com.apiimplementation.repository.AuthorRepository;
import com.apiimplementation.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public Book addBook(
            Integer authorId,
            CreateBookDto dto){

        Author author = authorRepository
                .findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Author Not Found"));

        Book book = new Book();

        book.setTitle(dto.getTitle());
        book.setSummary(dto.getSummary());
        book.setAuthor(author);
        return bookRepository.save(book);


    }

}
