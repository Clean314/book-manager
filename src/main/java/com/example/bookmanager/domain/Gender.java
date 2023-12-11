package com.example.bookmanager.domain;

// 주의! 기본적으로 DB 에서 Ordinary 이다.
// 별도로 설정해주지 않고 사용하면, 이후 새로운 필드를 추가했을 때 DB 와 완전히 달라져버릴 수 있다.
public enum Gender {
    FEMALE, // 0
    MALE // 1
    // 2, 3, 4, ...
}