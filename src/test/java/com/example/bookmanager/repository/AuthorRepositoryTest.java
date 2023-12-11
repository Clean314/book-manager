package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Author;
import com.example.bookmanager.domain.Book;
import com.example.bookmanager.domain.BookAndAuthor;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class AuthorRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookAndAuthorRepository bookAndAuthorRepository;

    @Test
    @Transactional
    public void manyToManyTest(){
        Book book1 = givenBook("책1");
        Book book2 = givenBook("책2");
        Book book3 = givenBook("책3");
        Book book4 = givenBook("책4");
        Author author1 = givenAuthor("저자1");
        Author author2 = givenAuthor("저자2");

//        book1.addAuthor(author1);
//        book2.addAuthor(author2);
//        book3.addAuthor(author1, author2);
//        book4.addAuthor(author1, author2);
//
//        author1.addBook(book1, book3, book4);
//        author2.addBook(book2, book3, book4);
//
//        bookRepository.saveAll(Lists.newArrayList(book1, book2, book3, book4));
//        authorRepository.saveAll(Lists.newArrayList(author1, author2));
//
//        System.out.println("authors : " + bookRepository.findAll().get(2).getAuthors());
//        System.out.println("reviews : " + authorRepository.findAll().get(0).getBooks());

        BookAndAuthor bookAndAuthor1 = givenBookAndAuthor(book1, author1);
        BookAndAuthor bookAndAuthor2 = givenBookAndAuthor(book2, author2);

        BookAndAuthor bookAndAuthor3 = givenBookAndAuthor(book3, author1);
        BookAndAuthor bookAndAuthor4 = givenBookAndAuthor(book3, author2);

        BookAndAuthor bookAndAuthor5 = givenBookAndAuthor(book4, author1);
        BookAndAuthor bookAndAuthor6 = givenBookAndAuthor(book4, author2);

        book1.addBookAndAuthor(bookAndAuthor1);
        book2.addBookAndAuthor(bookAndAuthor2);
        book3.addBookAndAuthor(bookAndAuthor3, bookAndAuthor4);
        book4.addBookAndAuthor(bookAndAuthor5, bookAndAuthor6);

        author1.addBookAndAuthor(bookAndAuthor1, bookAndAuthor3, bookAndAuthor4);
        author2.addBookAndAuthor(bookAndAuthor2, bookAndAuthor3, bookAndAuthor4);

        bookRepository.saveAll(Lists.newArrayList(book1, book2, book3, book4));
        authorRepository.saveAll(Lists.newArrayList(author1, author2));

        bookRepository.findAll().get(2).getBookAndAuthors().forEach(o -> {
            System.out.println(o.getAuthor());
        });
        authorRepository.findAll().get(0).getBookAndAuthors().forEach(o -> {
            System.out.println(o.getBook());
        });
    }

    public Book givenBook(String name){
        Book book = new Book();
        book.setName(name);
        return bookRepository.save(book);
    }

    public Author givenAuthor(String name){
        Author author = new Author();
        author.setName(name);
        return authorRepository.save(author);
    }

    public BookAndAuthor givenBookAndAuthor(Book book, Author author){
        BookAndAuthor bookAndAuthor = new BookAndAuthor();
        bookAndAuthor.setBook(book);
        bookAndAuthor.setAuthor(author);
        return bookAndAuthorRepository.save(bookAndAuthor);
    }
}
