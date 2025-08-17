# AI Interview Backend

AI 면접 준비를 위한 통합 플랫폼의 백엔드 레포지토리입니다. **Spring Boot**를 기반으로 안정적인 서버 환경을 구축하고, 비즈니스 로직 처리와 데이터 관리를 위한 RESTful API를 제공합니다.

---

### ## 주요 기능 및 API

백엔드 서버는 아래와 같은 핵심 기능들을 API로 제공하여 프론트엔드와 통신합니다.

1.  **회원 관리 및 인증 (User & Auth)**
    * `회원가입` 및 `로그인` 기능 제공
    * **JWT(JSON Web Token)**를 사용한 안정적인 인증 시스템
    * 사용자 개인정보 및 프로필(학력, 경력 등) 관리

2.  **자기소개서 (Cover Letter)**
    * 자기소개서 생성(Create), 조회(Read), 수정(Update), 삭제(Delete)를 위한 **CRUD API**
    * (향후) AI 모델과 연동하여 첨삭 및 분석 결과를 제공하는 로직 포함

3.  **마이페이지 (My Page)**
    * 사용자가 작성한 자기소개서, 스크랩한 채용 공고, 면접 기록 등 개인화된 데이터를 조회하는 API 제공

4.  **면접 준비 (Interview Prep)**
    * 직무/유형별 예상 질문 목록 제공
    * 관심 질문 즐겨찾기(Bookmark) 기능

5.  **실시간 AI 면접 (Interview Session)**
    * 면접 세션 생성 및 질문 목록 반환
    * 사용자의 답변 영상 및 음성 데이터 처리를 위한 API
    * 시선, 음성, 표정 등 분석 데이터를 저장하고 종합 리포트를 생성하는 기능

---

### ## 기술 스택 (Tech Stack)

* **Framework**: Spring Boot 3.x
* **Language**: Java 17
* **Database**: PostgreSQL (또는 MySQL)
* **Data Access**: Spring Data JPA
* **Security**: Spring Security, JWT
* **Build Tool**: Gradle

---

### ## 폴더 구조



---

### ## 시작하는 방법

프로젝트를 로컬 환경에서 실행하려면 아래 단계를 따라 주세요.

#### **1. 사전 준비**
* Java 17 (JDK) 설치
* PostgreSQL (또는 다른 관계형 데이터베이스) 설치 및 실행

#### **2. 프로젝트 설정**
```bash
# 레포지토리 클론
git clone [https://github.com/your-github-id/backend.git](https://github.com/your-github-id/backend.git)
cd backend


### 추후 작성 예정
