package com.example.bookmanager.repository;

import com.example.bookmanager.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    User findByEmail(String email);
    User findTop1ByName(String name);

    List<User> findByNameAndEmail(String name, String email);
    List<User> findByNameOrEmail(String name, String email);

    List<User> findByCreatedAtAfter(LocalDateTime createdAt);
    List<User> findByIdGreaterThan(Long id);

    List<User> findByIdBetween(Long id1, Long id2);
    List<User> findByIdGreaterThanEqualAndIdLessThanEqual(Long id1, Long id2);
    List<User> findByIdGreaterThanAndIdLessThan(Long id1, Long id2);

    List<User> findByIdIsNotNull();
//    List<User> findByAddressIsNotEmpty();

    List<User> findByNameIn(List<String> name);
    List<User> findByNameStartingWith(String name);
    List<User> findByNameEndingWith(String name);
    List<User> findByNameContains(String name);
    List<User> findByNameLike(String name);

    List<User> findFirstByNameOrderByIdDesc(String name);
    List<User> findFirstByName(String name, Sort sort);

    Page<User> findByName(String name, Pageable pageable);

    // 호출 시 쿼리를 그대로 실행하여 자동으로 맵핑된다.
    @Query(value = "select * from users limit 1", nativeQuery = true)
    Map<String, Object> findRowRecord();
}
