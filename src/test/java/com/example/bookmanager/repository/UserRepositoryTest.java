package com.example.bookmanager.repository;

import com.example.bookmanager.domain.Gender;
import com.example.bookmanager.domain.User;
import com.example.bookmanager.domain.UserHistory;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.endsWith;

@SpringBootTest
@Transactional // 각 테스트마다 rollback 을 해준다
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    void select(){
        // findAll
        // 성능 문제가 있다.
        System.out.println(">>> findAll <<<");
        userRepository
                .findAll(Sort.by(Sort.Direction.DESC, "name"))
                .forEach(System.out::println);

        // findAllById
        System.out.println(">>> findAllById <<<");
        userRepository
                .findAllById(Lists.newArrayList(1L, 3L, 5L))
                .forEach(System.out::println);

        // getOne
        // 세션을 유지하지 않기 때문에 @Transactional 이 필요하다.
        System.out.println(">>> getOne <<<");
        System.out.println(
                userRepository
                .getOne(1L));

        // findById
        // 존재하지 않는 경우를 위해서 Optional<User> 를 사용하거나 orElse 처리가 필요하다.
        System.out.println(">>> findById <<<");
        System.out.println(
                userRepository
                        .findById(1L)
                        .orElse(null)
        );
    }

    @Test
    void save(){
        // saveAll
        System.out.println(">>> saveAll <<<");
        userRepository
                .saveAll(Lists.newArrayList(
                        new User("user1", "user1@email.com"),
                        new User("user2", "user2@email.com")))
                .forEach(System.out::println);
    }

    @Test
    void delete(){
        // delete
        // select 가 두 번. select 2, delete 1
        // delete 에 들어가는 entity 는 null 이어선 안되기 때문에 예외를 발생시켰다.
        System.out.println(">>> delete <<<");
        userRepository.delete(userRepository.findById(1L).orElseThrow(RuntimeException::new));

        // deleteById
        // select 가 한 번만 호출되게 된다. select 1, delete 1
        System.out.println(">>> deleteById <<<");
        userRepository.deleteById(2L);

        System.out.println(">>> delete result <<<");
        userRepository.findAll().forEach(System.out::println);

        // deleteAll
        System.out.println(">>> deleteAll <<<");
        userRepository.deleteAll();
        System.out.println(">>> deleteAll result <<<");
        userRepository.findAll().forEach(System.out::println);

        // deleteById
        // 1L로 select, 3L로 select, 1L delete, 3L delete 될 것이다.
        System.out.println(">>> deleteById <<<");
        userRepository.deleteAll(
                userRepository.findAllById(Lists.newArrayList(1L, 3L))
        );

        // deleteInBatch 와 deleteAllInBatch 는 성능이 좀 개선된다. 다만 일괄처리는 메모리 사용량 증가

        // deleteInBatch
        // select 하지 않고, delete 도 따로따로 하지않음. 바로 쿼리를 던진다.
        System.out.println("deleteInBatch");
        userRepository.deleteInBatch(
                userRepository.findAllById(Lists.newArrayList(1L, 3L))
        );

        // deleteAllInBatch
        // select 가 아예 존재하지 않는다. 보다시피 where 도 없게 된다.
        System.out.println("deleteInBatch");
        userRepository.deleteAllInBatch();

        // select 를 해서 보느냐,
        // iterable 의 경우에는 하나씩 지우는 반복문을 돌리냐(deleteById),
        // 쿼리 한 번으로 지우냐(deleteInBatch) 등의 차이가 있다.
    }

    @Test
    void flush(){
        userRepository.save(new User()); userRepository.flush();
        // 위와 아래는 같다.
        userRepository.saveAndFlush(new User());
        
        // flush 는 쿼리가 아니라 DB 반영 시점을 조절하는 것. 이후 Context 공부할 때 자세히.
    }

    @Test
    void count(){
        long count = userRepository.count();
        boolean exist = userRepository.existsById(1L);
        System.out.println("Count : " + count);
        System.out.println("Exist : " + exist);
    }

    @Test
    void paging(){
        Page<User> userPage = userRepository.findAll(PageRequest.of(1, 3)); // zero based
        System.out.println("page : " + userPage);
        System.out.println("totalElements : " + userPage.getTotalElements()); // count 와 동일할 것임
        System.out.println("totalPages : " + userPage.getTotalPages()); // page size / elements = 전체가 몇 페이지인지. (5//3=2)
        System.out.println("numberOfElements" + userPage.getNumberOfElements()); // 현재 가져온 레코드 수
        System.out.println("sort : " + userPage.getSort());
        System.out.println("size : " + userPage.getSize()); // 페이징 크기

        userPage.getContent().forEach(System.out::println); // getContent 로 내부 정보를 볼 수 있다.

        // entity 가 현재 5개이고, 한 페이지당 수(size)를 3개로 정했다. 그러니까 [1, 2, 3] 과 [4, 5] 로 나눠질 것이다.
        // 즉, PageRequest.of(0, 3) 는 첫 번째 페이지인 것이고 PageRequest.of(1, 3) 는 두 번째 페이지인 것이다.
    }

    @Test
    void example(){
        // name 은 무시하고, email 이 email.com 으로 끝나는 것들로 조회하는 매쳐
        ExampleMatcher exampleMatcher1 = ExampleMatcher.matching()
                .withIgnorePaths("name") 
                .withMatcher("email", endsWith());

        Example<User> userExample1 = Example.of(new User("ma", "email.com"), exampleMatcher1); // 매쳐 적용
        userRepository.findAll(userExample1).forEach(System.out::println);

        // 매쳐 적용 X. 이제 name 과 email 이 정확히 일치하는 것이 조회가 될 것
        Example<User> userExample_noMatcher = Example.of(new User("ma", "email.com"));
        userRepository.findAll(userExample_noMatcher).forEach(System.out::println);

        // User 에 설정된 email 필드 내용을 포함하는(contains) 것들로 조회하는 매쳐
        ExampleMatcher exampleMatcher2 = ExampleMatcher.matching()
                .withMatcher("email", contains());
        
        User user = new User(); user.setEmail("email");
        Example<User> userExample2 = Example.of(user, exampleMatcher2);
        userRepository.findAll(userExample2).forEach(System.out::println);
    }

    @Test
    void update(){
        User user = userRepository.findById(1L).orElseThrow(RuntimeException::new);
        user.setEmail("update@email.com");
        userRepository.save(user); // JPA 에서 save 는 새로운 Entity 라면 insert, 존재한다면 update 한다.
    }

    @Test
    void findByTest(){
        System.out.println(">>> findByNameTest <<<");
        userRepository.findByName("ab").forEach(System.out::println);

        System.out.println(">>> findTop1ByNameTest <<<");
        System.out.println(userRepository.findTop1ByName("ab"));

        // Top2, First1 등도 가능하다.
        // 다만 Last 라는 키워드는 존재하지 않아서 무시되거나 오류가 날 것이다.

        System.out.println(">>> findByNameAndEmail <<<");
        userRepository.findByNameAndEmail("name", "email").forEach(System.out::println);
        userRepository.findByNameAndEmail("ab", "test1@test.com").forEach(System.out::println);

        System.out.println(">>> findByNameOrEmail <<<");
        userRepository.findByNameOrEmail("cd", "test1@test.com").forEach(System.out::println);

        // After 와 Before 는 AfterId 도 되긴하지만 날짜 등 시간에 쓰는 것이 좋다.
        System.out.println(">>> findByCreatedAtAfter <<<");
        // 어제 날짜를 넣으면 now 가 어제보다 크기 때문에 모두 나온다.
        userRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(1L)).forEach(System.out::println);

        // GraterThan 은 After, Before 와 쿼리가 동일하지만 좀 더 숫자에 쓰는 게 좋다.
        System.out.println(">>> findByIdGreaterThan <<<");
        userRepository.findByIdGreaterThan(3L).forEach(System.out::println);

        // GreaterThanEqual 이라 하면 같은 값도 포함한다.
        // 주의할 점은 After, Before = GreaterThan, LessThan 이고,
        // GreaterThanEqual, LessThanEqual 은 같은 값을 포함하는 쿼리이므로 저들과 같지 않다.

        // Between 은 시작과 끝을 포함한다.
        // 그러니 마치 LessThanEqual And GreaterThanEqual 이라고 볼 수 있다.
        System.out.println(">>> FindByIdBetween <<<");
        userRepository.findByIdBetween(1L, 3L).forEach(System.out::println);
        userRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L, 3L).forEach(System.out::println);
        
        // 이렇게 하면 시작과 끝을 포함하지 않는다.
        System.out.println(">>> findByIdGreaterThanAndIdLessThan <<<");
        userRepository.findByIdGreaterThanAndIdLessThan(1L, 3L).forEach(System.out::println);

        // 현재 Id 가 null 인 것은 없다
        System.out.println(">>> findByIdIsNotNull <<<");
        userRepository.findByIdIsNotNull().forEach(System.out::println);
        // IsEmpty, IsNotEmpty 는 collection 타입에만 쓸 수 있다. 빈 문자열 "" 을 가려내는 것이 아니다.
        System.out.println(">>> findByAddressIsNotEmpty <<<");
//        userRepository.findByAddressIsNotEmpty().forEach(System.out::println);

        // 리스트로 들어간 모든 문자열 중 일치하는 것들을 조회한다.
        System.out.println(">>> findByNameIn <<<");
        userRepository
                .findByNameIn(Lists.newArrayList("ab", "d")) // d 는 없으므로 조회되지 않을 것
                .forEach(System.out::println);

        // 3개 모두 각각의 뜻대로 조회된다.
        System.out.println(">>> findByNameStartingWith <<<");
        System.out.println(userRepository.findByNameStartingWith("a"));
        System.out.println(">>> findByNameEndingWith <<<");
        System.out.println(userRepository.findByNameEndingWith("f"));
        System.out.println(">>> findByNameContains <<<");
        System.out.println(userRepository.findByNameContains("e"));

        // 와일드카드를 이용한 Like 검색을 편리하게 사용할 수도 있다.
        System.out.println(">>> findByNameLike <<<");
        System.out.println(userRepository.findByNameLike("%h"));
    }

    @Test
    void pageSortingTest(){
        // 메소드 이름으로 정렬의 조건을 추가한다.
        System.out.println(">>> findFirstByNameOrderByIdDesc <<<");
        System.out.println(userRepository.findFirstByNameOrderByIdDesc("ab"));

        // Sort 가 복잡해지면 Naming 이 너무 길어지고 자유도가 떨어질 수도 있다.
        // 그럴 때는 findBy 필드와 Sort 인자를 넣어주는 방법을 사용하면 좋다.
        System.out.println(">>> findFirstByName with Sort <<<");
        System.out.println(userRepository.findFirstByName("ab", getSort()));

        // 크기 1 로 페이지를 나누고 id 내림차순 정렬한 결과의 두 번째 페이지를 본다.
        System.out.println(">>> findByName with Page <<<");
        System.out.println(
                userRepository.findByName(
                        "ab",
                        PageRequest.of(1, 1, Sort.by(Sort.Order.desc("id")))
                ).getContent()
        );
    }

    // 정렬 조건들을 반환하는 함수
    private Sort getSort(){
        return Sort.by(
                Sort.Order.desc("id"),
                Sort.Order.asc("email")
        );
    }

    @Test
    public void userHistoryTest(){
        System.out.println(">>> userHistoryTest <<<");
        User user = new User("old name", "email");
        userRepository.save(user);
        user.setName("new name");
        userRepository.save(user);
//        userRepository.findAll().forEach(System.out::println);
        System.out.println(userHistoryRepository.count());
        userHistoryRepository.findAll().forEach(System.out::println);
    }

    @Test
    public void userRelationTest(){
        User user = new User();
        user.setName("username");
        user.setEmail("email@email.com");
        user.setGender(Gender.FEMALE);
        userRepository.save(user);
        userRepository.flush(); // 즉시 반영해야 작동했다.

        user.setName("newName");
        userRepository.flush();

        user.setEmail("newEmail@email.com");
        userRepository.flush();

        userHistoryRepository.findAll().forEach(System.out::println);

        List<UserHistory> result = userHistoryRepository.findByUserId(
                userRepository.findByEmail("newEmail@email.com").getId());
        result.forEach(System.out::println);

        // OneToMany 를 사용해서 더 간단하게
        List<UserHistory> result2 = userRepository.findByEmail("newEmail@email.com").getUserHistories();
        result2.forEach(System.out::println);

        // ManyToOne
        // User 와 UserHistory 가 양방향이므로 서로 정보를 가져올 수 있다.
        User result3 = userHistoryRepository.findById(3L).orElse(null).getUser();
        System.out.println("result3 : "+result3);

        // 결론 : User 에서 UserHistory 를 참조하는 경우가 많을 것이므로 OneToMany 가 적합하다.
    }
}