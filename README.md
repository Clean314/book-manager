Spring에서 JPA, Hibernate, Mysql 을 공부한 내용을 정리한 것

[학습 내용]
0. JPA 정의 공부
1. data.sql, build.gradle 설정
2. Lombok 설정하고 테스트 해본다
관련 파일 : User.java, UserTest.java
3. H2 DB와 로그 설정
관련 파일 : build.gradle, application.yml, 
4. Entity를 만들고, JpaRepository의 기본적인 쿼리메소드를 테스트 해본다
관련 파일 : User.java, UserRepository.java, UserRepositoryTest.java 
5. 구체적인 쿼리메소드를 실습한다
관련 파일 : data.sql, UserRepositoryTest.java
6. 쿼리메소드로 페이징 정렬을 실습한다.
관련 파일 : UserRepositoryTest.java
7. Entity의 속성과 관련된 Annotation을 사용해본다.
관련 파일 : User.java, Gender.java, UserRepositoryTest.java
8. Entity의 Listener를 활용해본다.
