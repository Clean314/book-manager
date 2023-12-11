package com.example.bookmanager.domain;

import com.example.bookmanager.domain.listener.Auditable;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
// 이 클래스의 필드를, 이 클래스를 상속받는 클래스의 컬럼으로 포함시켜준다.
@MappedSuperclass
// BaseEntity (상위 클래스) 에서 리스너를 지정했기 때문에 이를 상속받는 클래스들은 리스너 설정이 필요없게 된다.
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Auditable {
    // Auditable 을 구현하는 클래스에서 필요한 변수들이다.
    // 하위 클래스에서 extends 해서 사용하면 된다.
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
