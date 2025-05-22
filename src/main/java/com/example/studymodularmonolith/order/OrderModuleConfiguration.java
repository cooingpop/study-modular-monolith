package com.example.studymodularmonolith.order;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.modulith.ApplicationModule;

@Configuration
@ComponentScan
@ApplicationModule(
    displayName = "Order Module",
    allowedDependencies = {"user", "product"}
)
public class OrderModuleConfiguration {
    // 이 클래스는 주문 모듈 경계를 정의합니다
    // @ApplicationModule 주석은 이를 모듈로 표시합니다
    // 허용된 종속성 속성은 이 모듈이 의존할 수 있는 다른 모듈을 정의합니다
    // 이 경우 주문 모듈은 사용자 및 제품 모듈에 따라 달라질 수 있습니다
}