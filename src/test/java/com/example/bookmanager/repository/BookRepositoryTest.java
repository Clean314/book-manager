package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Book;
import com.example.bookmanager.domain.Publisher;
import com.example.bookmanager.domain.Review;
import com.example.bookmanager.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class BookRepositoryTest {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void book(){
        Book book = new Book();
        book.setName("name");
        book.setAuthorId(1L);
//        book.setPublisherId(1L);
        bookRepository.save(book);
        System.out.println(">>> book test <<<");
//        System.out.println(bookRepository.count());
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void bookRelationTest(){
        givenInfo();
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        System.out.println("review : " + user.getReviews());
        System.out.println("book : " + user.getReviews().get(0).getBook());
        System.out.println("publisher : " + user.getReviews().get(0).getBook().getPublisher());
    }

    public void givenInfo(){
        Publisher publisher = new Publisher();
        publisher.setName("publisher name");
        Book book = new Book();
        book.setName("book name");
        book.setPublisher(publisherRepository.save(publisher));
        Review review = new Review();
        review.setTitle("review title");
        review.setContent("review content");
        review.setScore(5.0f);
        review.setBook(bookRepository.save(book));
        review.setUser(userRepository.findById(1L).orElseThrow(RuntimeException::new));
        reviewRepository.save(review);
    }
}
