package com.example.bookmanager.service;

import com.example.bookmanager.domain.User;
import com.example.bookmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class EntityManagerTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void entityManagerTest(){
        System.out.println(
                entityManager
                        .createQuery("select u from User u")
                        .getResultList()
        );
    }

    @Test
    public void cacheFindTest(){
        // Transactional 설정 시, findById 에 대해서 캐시된 결과를 가져온다. (EntityManager 에서 관리)
        System.out.println(userRepository.findByEmail("test1@test.com"));
        System.out.println(userRepository.findByEmail("test1@test.com"));
        System.out.println(userRepository.findByEmail("test1@test.com"));
        System.out.println(userRepository.findById(1L).get());
        System.out.println(userRepository.findById(1L).get());
        System.out.println(userRepository.findById(1L).get());
    }

    @Test
    public void cacheFindTest2(){
        // Transactional 에 의해서 2Update 쿼리가 1번 실행될 것임
        User user = new User();
        user.setName("name1");
        userRepository.save(user);

        System.out.println("----------------------");

        user.setEmail("email1@email.com");
        userRepository.save(user);

        userRepository.flush();
    }

    @Test
    public void cacheFindTest3(){
        User user = userRepository.findById(1L).get();
        user.setName("name1");
        userRepository.save(user);

        System.out.println("----------------------");

        user.setEmail("email1@email.com");
        userRepository.save(user);

        // DB 상에서 update 하지 않아도 출력은 정상적으로 나온다. 영속성 Context 와 DB 가 차이가 나는 순간
        System.out.println(userRepository.findById(1L).get());

        System.out.println("----------------------");
        userRepository.flush(); // DB 에서는 이 시점에서야 update 될 것이다.
    }

    @Test
    public void cacheFindTest4(){
        User user = userRepository.findById(1L).get();
        user.setName("name1");
        userRepository.save(user);

        System.out.println("----------------------");

        user.setEmail("email1@email.com");
        userRepository.save(user);

        System.out.println("----------------------");
        // 이 경우에는 영속성 Context 를 모두 실제로 DB 에 반영한다.
        userRepository.findAll().forEach(System.out::println); 
    }
}