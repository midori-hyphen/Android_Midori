# MIDORI

미림여고 기숙사 관리 앱

## 기술 스택

- **언어**: Kotlin 100%
- **UI**: Jetpack Compose
- **아키텍처**: Clean Architecture + MVVM
- **DI**: Hilt
- **비동기**: Coroutines + Flow + StateFlow
- **에러 처리**: Result Pattern
- **테스트**: JUnit4, Mockito, Coroutines Test

## 아키텍처

```
presentation/     # UI Layer (MVVM)
├── MainViewModel.kt
└── MainUiState.kt

domain/          # Business Logic Layer
├── usecase/     # Use Cases
└── common/      # Result Pattern

data/           # Data Layer
├── repository/ # Repository Pattern
├── models/     # Data Models
└── ui/         # UI State Models

di/             # Dependency Injection
components/     # UI Components
ui/theme/       # Design System
```

## 주요 기능

- 🎵 음악 위젯
- 🍽️ 식단 위젯  
- 📢 공지사항 위젯
- 📱 드래그 앤 드롭 지원
- 🔄 실시간 상태 관리

## 빌드 & 실행

```bash
./gradlew assembleDebug
./gradlew testDebugUnitTest
```

## 테스트

```bash
./gradlew test
./gradlew jacocoTestReport
```

테스트 커버리지: **90%+**

## 성능 최적화

- StateFlow 기반 상태 캐싱
- Mutex 동시성 제어
- WhileSubscribed 메모리 최적화
- Lazy 초기화 패턴

## 보안

- 네트워크 보안 설정
- ProGuard 난독화
- 인증서 핀닝
- 입력 검증

## CI/CD

GitHub Actions 자동화:
- 코드 품질 검사
- 단위 테스트 실행  
- APK 빌드
- 보안 검사 