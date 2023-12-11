package com.example.bookmanager.domain.listener;

import java.time.LocalDateTime;

// 공통적으로 리스너를 적용할 클래스를 정의해주기 위해서임
public interface Auditable {
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    public void setCreatedAt(LocalDateTime createdAt);
    public void setUpdatedAt(LocalDateTime updatedAt);
}
