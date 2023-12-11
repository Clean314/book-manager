package com.example.bookmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// 별도 클래스로 두면 Jpa metamodel 오류를 피할 수 있다.
//@EnableJpaAuditing // AuditingEntityListener 를 사용하기 위함이다.
public class BookManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagerApplication.class, args);
	}

}
