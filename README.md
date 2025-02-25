# 🏡 Family Community
> **가족과 같은 커뮤니티를 위한 소셜 게시판 애플리케이션**

## 📌 프로젝트 개요
Family Community는 사용자가 **회원가입 및 로그인**을 통해 인증을 거친 후, **게시판을 생성, 수정, 삭제**할 수 있는 웹 애플리케이션입니다.

## 🚀 주요 기능
### 1. **사용자 인증 (JWT 기반 로그인)**
- 회원가입 (`/api/auth/register`)
- 로그인 (`/api/auth/login`)
- 회원 탈퇴 (`/api/auth/delete`)
- JWT 토큰을 이용한 인증 및 보안 강화

### 2. **게시판 기능**
- **게시글 생성** (`POST /api/board`)
- **게시글 조회** (`GET /api/board/{id}`)
- **게시글 목록 조회** (`GET /api/board`)
- **게시글 수정** (`PUT /api/board/{id}`)
- **게시글 삭제** (`DELETE /api/board/{id}`)
- **이미지 업로드 기능** 포함

### 3. **보안 및 접근 권한**
- Spring Security + JWT 적용
- 로그인된 사용자만 게시글을 생성, 수정, 삭제 가능

### 4. **예외 처리 및 에러 핸들링**
- Spring Boot 예외 처리 (`@ControllerAdvice` 활용)
- 유효하지 않은 요청에 대한 검증 (`@Valid`, `@ExceptionHandler`)

## 🛠️ API 사용법
### 1️⃣ 회원가입 API
```http
POST /api/auth/register
```
**Request Body:**
```json
{
  "email": "testuser@example.com",
  "password": "password123"
}
```
**Response:**
```json
{
  "message": "회원가입 성공",
  "token": "JWT_ACCESS_TOKEN"
}
```

### 2️⃣ 로그인 API
```http
POST /api/auth/login
```
**Request Body:**
```json
{
  "email": "testuser@example.com",
  "password": "password123"
}
```
**Response:**
```json
{
  "token": "JWT_ACCESS_TOKEN"
}
```

### 3️⃣ 게시글 생성 API
```http
POST /api/board
```
**Request Header:**
```
Authorization: Bearer JWT_ACCESS_TOKEN
Content-Type: multipart/form-data
```
**Request Body (Form-Data):**
```
key: title -> "게시글 제목"
key: content -> "게시글 내용"
key: file (선택 사항, 이미지 업로드 가능)
```
**Response:**
```json
{
  "id": 1,
  "title": "게시글 제목",
  "content": "게시글 내용",
  "imageUrl": "http://localhost:8082/uploads/image.jpg",
  "createdAt": "2025-02-25T12:34:56"
}
```

### 4️⃣ 게시글 수정 API
```http
PUT /api/board/{id}
```
**Request Header:**
```
Authorization: Bearer JWT_ACCESS_TOKEN
Content-Type: multipart/form-data
```
**Request Body (Form-Data):**
```
key: title -> "수정된 제목"
key: content -> "수정된 내용"
key: file (선택 사항, 이미지 변경 가능)
```
**Response:**
```json
{
  "id": 1,
  "title": "수정된 제목",
  "content": "수정된 내용",
  "imageUrl": "http://localhost:8082/uploads/new_image.jpg",
  "updatedAt": "2025-02-25T12:45:00"
}
```

### 5️⃣ 게시글 삭제 API
```http
DELETE /api/board/{id}
```
**Request Header:**
```
Authorization: Bearer JWT_ACCESS_TOKEN
```
**Response:**
```json
{
  "message": "게시글이 삭제되었습니다."
}
```

## 🔧 기술 스택
- **Backend:** Java 21, Spring Boot 3.4, Spring Security, JWT, JPA, MySQL, H2 (테스트)
- **Frontend:** React (개발 예정)
- **Deployment:** Docker (예정)

## 🎯 앞으로 추가할 기능
✅ **프론트엔드 개발 (React 기반)**
✅ **게시판 댓글 기능** (`/api/comment`)
✅ **좋아요 기능** (`/api/board/{id}/like`)
✅ **외부 API 연동 (게임 전적 검색 API 활용 고려)**
✅ **회원 프로필 및 이미지 업로드 기능**
✅ **반응형 디자인 및 UI 개선**

## 📜 실행 방법
### 1️⃣ 프로젝트 클론
```sh
git clone https://github.com/Sn-bow/family-community.git
cd family-community
```

### 2️⃣ Backend 실행 (Spring Boot)
```sh
./gradlew bootRun
```
또는
```sh
mvn spring-boot:run
```

### 3️⃣ Frontend 실행 (React, 개발 예정)
```sh
cd frontend
npm install
npm start
```

## 📜 환경 변수 설정
`.env` 파일을 프로젝트 루트에 생성하고, 다음과 같이 설정하세요.
```env
# 데이터베이스 설정
DB_URL=jdbc:mysql://localhost:3306/community
DB_USERNAME=root
DB_PASSWORD=yourpassword

# JWT 설정
JWT_SECRET=your_jwt_secret
```


---

🎉 **기능이 계속 추가될 예정이니 많은 관심 부탁드립니다!** 🚀

