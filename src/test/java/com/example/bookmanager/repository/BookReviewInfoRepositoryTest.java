package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.bookmanager.domain.BookReviewInfo;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookReviewInfoRepositoryTest {
    @Autowired
    private BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void crudTest(){
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
//        bookReviewInfo.setBookId(1L);
        bookReviewInfo.setBook(givenBookInfo());
        bookReviewInfo.setReviewCount(2);
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfoRepository.save(bookReviewInfo);

        System.out.println(">>> " + bookReviewInfoRepository.findAll());
    }

    @Test
    void crudTest2(){
        givenBookReviewInfo();

        // id로 찾기 실패함. 공용으로 hibernate_sequence 를 사용하기 때문에 1 차이가 날 것이다.
        // -> IDENTITY TYPE 으로 해결
//        Book result = bookRepository.findById(
//                bookReviewInfoRepository
//                        .findById(1L)
//                        .orElseThrow(RuntimeException::new)
//                        .getBookId()
//        ).orElseThrow(RuntimeException::new);
        // -> 간접 참조 방법임

        // bookReview 에서 Book 을 바로 참조할 수 있다.
        Book result = bookReviewInfoRepository
                .findById(1L)
                .orElseThrow(RuntimeException::new)
                .getBook();

        System.out.println(">>> " + result);

        // book 에서 bookReview 참조하기
        BookReviewInfo result2 = bookRepository
                .findById(7L) // id 값이 증가하지만 롤백 설정됨. 첫 데이터라도 id가 7로 되어있을 것이다.
                .orElseThrow(RuntimeException::new)
                .getBookReviewInfo();

        System.out.println(">>> " + result2);
    }

    Book givenBookInfo(){
        Book book = new Book();
        book.setName("Book name");
        book.setAuthorId(1L);
//        book.setPublisherId(1L);
        return bookRepository.save(book);
    }
    void givenBookReviewInfo(){
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(givenBookInfo());
        bookReviewInfo.setReviewCount(2);
        bookReviewInfo.setAverageReviewScore(4.5f);
        bookReviewInfoRepository.save(bookReviewInfo);
    }
}