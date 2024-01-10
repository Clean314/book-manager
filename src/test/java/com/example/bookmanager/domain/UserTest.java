package com.example.bookmanager.domain;

import com.example.bookmanager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void test(){ //Lombok Test
        User noUser = new User();
        noUser.setName("noUser");
        noUser.setEmail("noUser@email.com");

        User allUser = new User(
                null,
                "allUser",
                "allUser@email.com",
                null,
                null,
                null
        );

        User requiredUser = new User(
                "requiredUser",
                "requiredUser@email.com"
        );

        System.out.println("noUser >>> " + noUser);
        System.out.println("allUser >>> " + allUser);
        System.out.println("requiredUser >>> " + requiredUser);
    }

    @Test
    void test1(){
        System.out.println(">>> test1 <<<");
        System.out.println(userRepository.findRowRecord().get("name"));
    }

    @Test
    void persistenceTest() throws InterruptedException {
        /*
            주의!
            data.sql 에 작성된 초기 데이터에 대해서는 JPA 의 콜백 메소드가 작동하지 않으므로
            persistence 가 적용되지 않는다고 한다. 초기 설정이 필요하다면 직접 잘 작성해줘야 한다.
            EntityManager 로 되게 하는 방법이 있다고는 하는데 나중에 공부해보자.
        */

        // 하나 불러오기
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);

        // 업데이트
        user.setName("updated");
        userRepository.save(user);

        // 시간 확인해보기. 둘 다 now() 였으므로 조금이라도 다르면 된다.
        System.out.println("persistenceTest : " + userRepository.findAll().get(0));
    }
}