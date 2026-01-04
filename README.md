# 24-AI-Interview Backend

> **AI 기술을 활용한 지능형 모의 면접 및 자기소개서 분석 플랫폼**
> 사용자의 자소서를 기반으로 질문을 생성하고, 면접 중 실시간 시선, 음성, 표정을 분석하여 객관적인 리포트를 제공합니다.

---

## 1. 주요 기능 및 API (Core Features & API)

### 자기소개서 & AI 분석
* **자소서 분석**: 업로드된 파일(PDF/Word)에서 텍스트를 추출하고 AI가 핵심 키워드 및 예상 질문 도출
* `POST /api/selfintro/analyze` - 자소서 분석 및 질문 생성

### AI 인적성 검사 (Test)
* **부정행위 방지**: 테스트 시작 전 카메라/마이크 권한 및 환경 체크
* **실시간 분석**: 검사 중 사용자의 페이스 트래킹 데이터 전송
* `POST /api/test/start` - 검사 환경 체크 및 시작
* `POST /api/test/submission` - 답변 및 분석 데이터 제출

### AI 모의 면접 (Interview)
* **멀티모달 분석**: 면접 중 시선(Gaze), 음성(Voice), 표정(Expression) 데이터를 실시간으로 수집 및 분석
* **종합 리포트**: STT 결과와 NLP 분석을 결합하여 답변의 논리력과 태도 피드백 생성
* `GET /api/interview/questions` - 면접 시작 및 질문 목록 조회
* `POST /api/interview/record` - 면접 답변 영상 업로드
* `POST /api/interview/report` - 최종 분석 리포트 생성

### 마이페이지 & 준비 (MyPage & Prep)
* **이력 관리**: 과거 면접 영상 다시 보기 및 분석 리포트 히스토리 관리
* **질문 북마크**: 직무별 예시 질문을 즐겨찾기하고 연습 모드 활용
* `GET /api/mypage/profile` - 사용자 이력 정보 조회
* `GET /api/prep/questions` - 면접 준비용 예시 질문 조회
* `POST /api/prep/bookmark` - 질문 즐겨찾기 등록

---

## 2. 기술 스택 (Tech Stack)

### **Backend**
* **Language**: Java 17
* **Framework**: Spring Boot 3.x
* **Build Tool**: Gradle
* **Security**: Spring Security (JWT 기반 인증 구현 중)

### **Database & Library**
* **ORM**: Spring Data JPA (Hibernate)
* **Database**: MySQL
* **File Parsing**: Apache Tika (자소서 텍스트 추출용)
* **Utility**: Lombok, MapStruct

---

## 3. 폴더 구조 (Project Structure)

설계서의 도메인별로 책임을 명확히 분리한 구조입니다.

```text
src/main/java/com/example/interviewhelper/
├── controller/          # API 엔드포인트 (Auth, Interview, MyPage, Prep, Test 등)
├── service/             # 비즈니스 로직 (AI 연동, 데이터 처리, 파일 저장)
├── repository/          # DB 접근 인터페이스 (JPA Repository)
├── entity/              # DB 테이블 매핑 클래스 (User, Session, Report 등)
└── dto/                 # 계층 간 데이터 전송 객체
    ├── user/            # 로그인, 회원가입 관련 DTO
    ├── interview/       # 면접 세션 및 결과 관련 DTO
    ├── coverletter/     # 자소서 분석 관련 DTO
    └── prep/            # 면접 준비 및 북마크 DTO
```


## 4. 시작하는 방법 (Getting Started)

### **Prerequisites**
* **Java 17** 이상 설치 필수
* **IDE**: IntelliJ IDEA 권장

### **Installation & Run**

1. **저장소 클론**
   ```bash
   git clone [https://github.com/24-AI-Interview/backend.git](https://github.com/24-AI-Interview/backend.git)
   cd backend
   ```

2. **환경 설정**
    src/main/resources/application.yml 파일에서 아래 설정을 본인 환경에 맞게 수정합니다.
    ```bash
    file:
    upload-dir: /your/path/to/uploads/ # 영상 및 파일 저장 경로
    ```

3. **애플리케이션 실행:
    ```bash
    ./gradlew bootRun
    ```

## 5. 기여 방법

1.  **이슈 등록**: 해결하고자 하는 문제나 추가하고 싶은 기능에 대해 이슈를 먼저 생성합니다.
2.  **브랜치 생성**: `feat/기능-설명`, `fix/버그-설명`, `refactor/리팩토링-내용`과 같은 네이밍 규칙을 사용하여 작업 브랜치를 생성합니다.
3.  **코드 작성 및 PR**: 작업 완료 후, `main` 브랜치로 Pull Request를 보냅니다.
4.  **코드 리뷰**: 팀원들의 코드 리뷰를 거친 후 `main` 브랜치에 병합됩니다.

**참고**: `main` 브랜치에 직접 커밋하는 것은 금지되어 있으며, 모든 변경 사항은 PR을 통해 병합됩니다.
