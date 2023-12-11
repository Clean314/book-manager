package com.example.bookmanager.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    // 우선 ApplicationContext 를 주입받는다.
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    // 스프링 컨텍스트에서 해당 클래스 타입의 Bean 을 찾아서 반환하는 것이다.
    public static <T> T getBean(Class<T> tClass){
        return applicationContext.getBean(tClass);
    }
    /*
       <T> 는 제네릭, T 는 매개변수임.
       즉 Object 가 아니라 반환 타입이 제네릭 타입 파라미터 T 로 명시되도록 해야 함.
    */
}
