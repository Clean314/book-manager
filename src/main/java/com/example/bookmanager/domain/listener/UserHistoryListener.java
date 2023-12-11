package com.example.bookmanager.domain.listener;

import com.example.bookmanager.domain.User;
import com.example.bookmanager.domain.UserHistory;
import com.example.bookmanager.repository.UserHistoryRepository;
import com.example.bookmanager.support.BeanUtils;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

//@Component // AutoWired 같이 Bean 관리하려면 컴포넌트 등록해줘야한다.
public class UserHistoryListener {

    // 이렇게 주입받도록 했지만, Listener 클래스는 Bean 을 주입받지 못한다.
//    @Autowired
//    private UserHistoryRepository userHistoryRepository;

    // Pre 를 사용할 경우 insert 시 userId 가 기록되지 않는다.
    // insert 전에 PrePersist 가 동작하기 때문인데, 이 때 추가된 user 가 존재하지 않기 때문
//    @PrePersist
//    @PreUpdate
    @PostPersist
    @PostUpdate
    public void prePersistAndUpdate(Object object){
        // 그래서 이렇게 주입받기
        UserHistoryRepository userHistoryRepository =
                BeanUtils.getBean(UserHistoryRepository.class);

        User user = (User) object;
        
        UserHistory userHistory = new UserHistory();
//        userHistory.setUserId(user.getId());
        userHistory.setName(user.getName());
        userHistory.setEmail(user.getEmail());
        userHistory.setUser(user);

        userHistoryRepository.save(userHistory);
    }
}
