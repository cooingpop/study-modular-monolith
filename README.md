# 모듈형 모놀리스 아키텍처 스터디 프로젝트

이 프로젝트는 Spring Modulith를 사용한 모듈형 모놀리스 아키텍처를 구현한 샘플 애플리케이션입니다. 각 모듈은 자체 도메인, API 및 구현 세부 사항을 가지고 있으면서도 단일 애플리케이션으로 배포됩니다.

## 아키텍처 개요

이 애플리케이션은 다음과 같은 특징을 가진 모듈형 모놀리스 아키텍처를 따릅니다:

- 각 모듈은 명확한 경계를 가진 별도의 패키지로 구성
- 모듈 간 통신은 잘 정의된 인터페이스와 이벤트를 통해 이루어짐
- 모듈 간 의존성은 명시적으로 선언되고 제어됨
- 전체 애플리케이션은 단일 유닛으로 배포됨

## 모듈 구성

애플리케이션은 다음과 같은 모듈로 구성되어 있습니다:

### 사용자 모듈 (User Module)

- 사용자 관리 담당
- 사용자 등록, 인증 및 프로필 관리 기능 제공
- `com.example.studymodularmonolith.user` 패키지에 위치

### 상품 모듈 (Product Module)

- 상품 카탈로그 관리
- 상품 생성, 조회 및 재고 관리 기능 제공
- `com.example.studymodularmonolith.product` 패키지에 위치

### 주문 모듈 (Order Module)

- 주문 처리 담당
- 주문 생성, 상태 업데이트 및 주문 이력 기능 제공
- 사용자 모듈과 상품 모듈에 의존
- `com.example.studymodularmonolith.order` 패키지에 위치

### 알림 모듈 (Notification Module)

- 사용자 알림 관리
- 알림 생성, 조회 및 전송 기능 제공
- 사용자 모듈과 주문 모듈에 의존
- `com.example.studymodularmonolith.notification` 패키지에 위치

## 모듈 경계 정의

모듈 경계는 Spring Modulith의 `@ApplicationModule` 어노테이션을 사용하여 강제됩니다. 각 모듈은 경계와 허용된 의존성을 정의하는 구성 클래스를 가지고 있습니다:

```java
@Configuration
@ComponentScan
@ApplicationModule(
    displayName = "모듈 이름",
    allowedDependencies = {"다른-모듈"}
)
public class ModuleConfiguration {
    // 모듈 구성
}
```

## 모듈 간 통신

모듈은 다음과 같은 방식으로 서로 통신합니다:

1. **직접 메서드 호출**: 한 모듈이 다른 모듈에 직접 의존하는 동기 작업에 사용
2. **이벤트**: 비동기 작업 및 모듈 간 느슨한 결합을 위해 사용

이벤트 기반 통신 예시:

```java
// 주문 모듈에서 이벤트 발행
@Service
public class OrderService {
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void createOrder(Order order) {
        // 주문 처리 로직
        // ...

        // 이벤트 발행
        eventPublisher.publishEvent(OrderEvent.orderCreated(order));
    }
}

// 알림 모듈에서 이벤트 수신
@Component
public class OrderEventListener {
    private final NotificationService notificationService;

    public OrderEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @EventListener
    public void handleOrderCreatedEvent(OrderEvent event) {
        // 이벤트 처리
        notificationService.sendOrderConfirmation(event.getUserId(), event.getOrderId());
    }
}
```

## 이 아키텍처의 장점

1. **모듈성**: 관심사와 책임의 명확한 분리
2. **유지보수성**: 한 모듈의 변경이 다른 모듈에 미치는 영향 최소화
3. **테스트 용이성**: 모듈을 독립적으로 테스트 가능
4. **확장성**: 필요시 모듈을 별도의 서비스로 추출 가능
5. **단순성**: 단일 배포 단위로 운영 간소화

## 시작하기

1. 저장소 복제
2. `./gradlew bootRun` 명령으로 애플리케이션 실행
3. `http://localhost:8080`에서 API 접근

## API 엔드포인트

애플리케이션은 각 모듈에 대한 REST API를 제공합니다:

- 사용자 API: `/api/users`
- 상품 API: `/api/products`
- 주문 API: `/api/orders`
- 알림 API: `/api/notifications`

## 사용된 기술

- Java 21
- Spring Boot 3.4.5
- Spring Modulith 1.3.5
- Gradle

## 프로젝트 구조

```
src/main/java/com/example/studymodularmonolith/
├── StudyModularMonolithApplication.java
├── user/
│   ├── UserModuleConfiguration.java
│   ├── api/
│   ├── domain/
│   ├── repository/
│   └── service/
├── product/
│   ├── ProductModuleConfiguration.java
│   ├── api/
│   ├── domain/
│   ├── repository/
│   └── service/
├── order/
│   ├── OrderModuleConfiguration.java
│   ├── api/
│   ├── domain/
│   ├── event/
│   ├── repository/
│   └── service/
└── notification/
    ├── NotificationModuleConfiguration.java
    ├── api/
    ├── domain/
    ├── listener/
    ├── repository/
    └── service/
```
