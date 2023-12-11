--IDENTITY 를 사용했다면 해당하는 sequence 를 사용하도록 hibernate_sequence 를 주석처리했다.
--call next value for hibernate_sequence; -- id 값을 계속해서 반영하기 위함
insert into users (`id`, `name`, `email`, `created_at`, `updated_at`) values (1, 'ab', 'test1@test.com', now(), now());

--call next value for hibernate_sequence;
insert into users (`id`, `name`, `email`, `created_at`, `updated_at`) values (2, 'cd', 'test2@test.com', now(), now());

--call next value for hibernate_sequence;
insert into users (`id`, `name`, `email`, `created_at`, `updated_at`) values (3, 'ef', 'test3@test.com', now(), now());

--call next value for hibernate_sequence;
insert into users (`id`, `name`, `email`, `created_at`, `updated_at`) values (4, 'ab', 'test4@email.com', now(), now());

--call next value for hibernate_sequence;
insert into users (`id`, `name`, `email`, `created_at`, `updated_at`) values (5, 'gh', 'test5@email.com', now(), now());