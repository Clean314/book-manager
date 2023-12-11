package com.example.bookmanager.service;

import com.example.bookmanager.domain.Author;
import com.example.bookmanager.domain.Book;
import com.example.bookmanager.repository.AuthorRepository;
import com.example.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public void putBookAndAuthor(){
        Book book = new Book();
        book.setName("book1");
        bookRepository.save(book);

        Author author = new Author();
        author.setName("author1");
        authorRepository.save(author);
    }
}
