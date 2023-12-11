package com.example.bookmanager.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
//@EntityListeners(value = AuditingEntityListener.class) // 기본 제공 리스너를 추가하여 별도 메소드 작성없이 편리하게!
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 테이블마다 개별적 sequence 를 사용하게 함
    private Long id;

    private String name;

    private String category;

    private Long authorId;

    @OneToOne(mappedBy = "book")
    @ToString.Exclude // ToString 에서 제외된다. 순환 참조 발생 위험
    private BookReviewInfo bookReviewInfo;

    @OneToMany
    @JoinColumn(name = "book_id") // oneToMany 일 때 중간 테이블이 생성되지 않기 위해서 book_id 로 join
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne
    @ToString.Exclude
    private Publisher publisher;

//    @ManyToMany
    @OneToMany
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthor(BookAndAuthor... bookAndAuthors){
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }

//    @CreatedDate // AuditingEntityListener 를 활용
//    private LocalDateTime createdAt;
//    @LastModifiedDate // AuditingEntityListener 를 활용
//    private LocalDateTime updatedAt;

//    @PrePersist
//    private void perPersist(){
//        this.createdAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    private void perUpdate(){
//        this.updatedAt = LocalDateTime.now();
//    }r
}
