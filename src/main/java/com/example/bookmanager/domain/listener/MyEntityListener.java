package com.example.bookmanager.domain.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// 사실 이 클래스의 기능은
// org.springframework.data.jpa.domain.support.AuditingEntityListener 에서 제공한다.
public class MyEntityListener {
    @PrePersist
    public void prePersist(Object object){
        if (object instanceof Auditable) {
            ((Auditable)object).setCreatedAt(LocalDateTime.now());
            ((Auditable)object).setUpdatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object object){
        if (object instanceof Auditable) {
            ((Auditable)object).setUpdatedAt(LocalDateTime.now());
        }
    }
}
