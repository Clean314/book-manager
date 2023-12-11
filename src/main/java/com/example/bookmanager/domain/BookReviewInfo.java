package com.example.bookmanager.domain;

import com.example.bookmanager.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ToString(callSuper = true) // 상속 정보를 ToString 에 포함
@EqualsAndHashCode(callSuper = true)
public class BookReviewInfo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long bookId;

    @OneToOne(optional = false)
    private Book book;

    // primitive type 은 null 체크가 필요없다.
    private float averageReviewScore;
    private int reviewCount;
}
