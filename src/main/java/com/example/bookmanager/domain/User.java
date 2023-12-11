package com.example.bookmanager.domain;

import javax.persistence.*;

import com.example.bookmanager.domain.listener.Auditable;
import com.example.bookmanager.domain.listener.UserHistoryListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor

// NonNull 이 사용된 필드에 대해 필수 값을 요구한다.
// @Data 에 포함되지만 위에 다른 Constructor 가 존재하기 때문에 지정한다.
@RequiredArgsConstructor

// @EqualsAndHashCode 는 equals()와 hashCode() 메서드를 자동으로 생성. @Data 에 포함됨
// equals()는 두 객체가 서로 같은지를 비교하고, hashCode()는 객체를 해시 테이블에 넣을 때 사용

@Data
@Builder
@Entity
// user 는 h2 에서 예약어라서 오류가 났다.
@Table(name="users",
        indexes = {@Index(columnList = "name")},
        uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
// Listener 는 여러 개가 가능하다.
//@EntityListeners(value = {AuditingEntityListener.class}) // 기본 제공으로 사용하기
@EntityListeners(value = {UserHistoryListener.class}) // BaseEntity 를 사용해도 있어야 한다. userHistory 라는 추가 기능
//@EntityListeners(value = {MyEntityListener.class, UserHistoryListener.class}) 대체됨
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    @NonNull
    private String email;

    // 이렇게 하면 숫자 순서가 아니라 문자열이 그대로 저장되게 된다.
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    @Builder.Default
    @ToString.Exclude
    private List<UserHistory> userHistories = new ArrayList<>(); // null point 예외 방지

    @OneToMany
    @JoinColumn(name = "user_id")
    @Builder.Default
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

//    // JPA 에서 일반적으로 Time 을 포함하도록 되어있다
//    @Column(updatable = false)
//    @CreatedDate // AuditingEntityListener 사용 시 필요
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;

    // DB 에서 사용되지 않는 필드로 정해준다.
//    @Transient
//    private String testData;


    // 감시용으로 쓸 수 있다.
//    @PrePersist : insert 전 -> 많이 사용
//    @PostPersist : insert 후
//    @PreUpdate : update 전 -> 많이 사용
//    @PostUpdate : update 후
//    @PreRemove :  remove 전
//    @PostRemove : remove 후
//    @PostLoad : select 후

    // 다음처럼 활용하는 게 인스턴스에 시간 값을 직접 넣는 것보다 더 적합할 수 있다.

//    @PrePersist
//    private void prePersist(){
//        System.out.println("[prePersist-log]");
//        this.createdAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    private void preUpdate(){
//        System.out.println("[preUpdate-log]");
//        this.updatedAt = LocalDateTime.now();
//    }
}
