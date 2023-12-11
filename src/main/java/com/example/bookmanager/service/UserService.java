package com.example.bookmanager.service;

import com.example.bookmanager.domain.User;
import com.example.bookmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void put() { // 비영속
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.com");
    }

    @Transactional
    public void put2(){ // 영속화
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.com");

//        userRepository.save(user); Repository 에서도 EM 을 포함하기 때문에 같은 효과가 난다.
        entityManager.persist(user);

        user.setName("new name"); // 이것도 반영해준다.
    }

    @Transactional
    public void put3(){ // 영속화
        User user = new User();
        user.setName("name");
        user.setEmail("email@email.com");
        
        entityManager.persist(user); // 반영 등록
        entityManager.detach(user); // 반영 해제

        user.setName("new name");
        entityManager.merge(user); // 작업 반영 예약

        entityManager.flush(); // 이전 작업 반영
        entityManager.clear(); // 이전 작업 삭제
    }

    @Transactional
    public void remove(){
        User user = userRepository.findById(1L).get();
        entityManager.remove(user); // 첫 번째 삭제
    }
}
