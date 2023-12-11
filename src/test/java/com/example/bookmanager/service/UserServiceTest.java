package com.example.bookmanager.service;

import com.example.bookmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void putTest(){ // 비영속
        userService.put();
        System.out.println(">>> " + userRepository.findByEmail("email@email.com"));
    }

    @Test
    public void put2Test(){ // 영속화
        userService.put2();
        System.out.println(">>> " + userRepository.findByEmail("email@email.com"));
    }

    @Test
    public void put3Test(){ // 영속화2
        userService.put3();
        System.out.println(">>> " + userRepository.findByEmail("email@email.com"));
    }

    @Test
    public void removeTest(){
        userService.remove();
        userRepository.findAll().forEach(System.out::println);
    }
}