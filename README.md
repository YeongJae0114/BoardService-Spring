
# 게시판 API 문서

## 1. Posts

### **POST /posts (게시글 생성)**

- **설명**: 새로운 게시글을 생성합니다.
- **Request Body**:
  ```json
  {
    "title": "게시글 제목",
    "content": "게시글 내용"
  }
  ```
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "OK"
      },
      "data": {
        "id": 1,
        "title": "게시글 제목",
        "content": "게시글 내용",
        "author": "작성자 이름",
        "createdDate": "2024-12-12T12:00:00"
      }
    }
    ```
  - **실패**:
    ```json
    {
      "status": {
        "code": 4001,
        "message": "잘못된 요청"
      },
      "data": null
    }
    ```

---

### **GET /posts/{id} (게시글 단건 조회)**

- **설명**: 특정 게시글을 ID로 조회합니다.
- **Path Parameter**:
  - `id`: 게시글 ID
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "OK"
      },
      "data": {
        "id": 1,
        "title": "게시글 제목",
        "content": "게시글 내용",
        "author": "작성자 이름",
        "createdDate": "2024-12-12T12:00:00"
      }
    }
    ```

---

### **GET /posts (게시글 전체 조회)**

- **설명**: 모든 게시글을 조회합니다.
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "OK"
      },
      "data": [
        {
          "id": 1,
          "title": "게시글 제목",
          "content": "게시글 내용",
          "author": "작성자 이름",
          "createdDate": "2024-12-12T12:00:00"
        },
        ...
      ]
    }
    ```

---

### **PUT /posts/{id} (게시글 수정)**

- **설명**: 특정 게시글을 수정합니다.
- **Path Parameter**:
  - `id`: 게시글 ID
- **Request Body**:
  ```json
  {
    "title": "수정된 제목",
    "content": "수정된 내용"
  }
  ```
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "수정 완료"
      },
      "data": null
    }
    ```

---

### **DELETE /posts/{id} (게시글 삭제)**

- **설명**: 특정 게시글을 삭제합니다.
- **Path Parameter**:
  - `id`: 게시글 ID
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "삭제 완료"
      },
      "data": null
    }
    ```

---

### **GET /posts?offset={offset}&limit={limit} (페이징 기반 조회)**

- **설명**: 게시글을 페이징으로 조회합니다.
- **Query Parameters**:
  - `offset`: 시작 위치 (기본값: 0)
  - `limit`: 조회 개수 (기본값: 10)
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "OK"
      },
      "data": [
        {
          "id": 1,
          "title": "게시글 제목",
          "content": "게시글 내용",
          "author": "작성자 이름",
          "createdDate": "2024-12-12T12:00:00"
        },
        ...
      ]
    }
    ```

---

### **GET /posts/cursor?cursorId={cursorId}&createdDateCursor={createdDateCursor} (커서 기반 조회)**

- **설명**: 커서 기반으로 게시글을 조회합니다.
- **Query Parameters**:
  - `cursorId`: 마지막 게시글 ID
  - `createdDateCursor`: 마지막 게시글 생성일
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "OK"
      },
      "data": [
        {
          "id": 1,
          "title": "게시글 제목",
          "content": "게시글 내용",
          "author": "작성자 이름",
          "createdDate": "2024-12-12T12:00:00"
        },
        ...
      ],
      "nextCursor": {
        "id": 10,
        "createdDate": "2024-12-12T11:00:00"
      }
    }
    ```

---

## 2. User

### **POST /user/signup (회원가입)**

- **설명**: 새로운 사용자를 등록합니다.
- **Request Body**:
  ```json
  {
    "username": "user123",
    "email": "user123@example.com",
    "password": "password123"
  }
  ```
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "회원가입 완료"
      },
      "data": null
    }
    ```

---

### **POST /user/login (로그인)**

- **설명**: 사용자 로그인
- **Request Body**:
  ```json
  {
    "email": "user123@example.com",
    "password": "password123"
  }
  ```
- **Response**:
  - **성공**:
    ```json
    {
      "status": {
        "code": 2000,
        "message": "로그인 성공"
      },
      "data": null
    }
    ```
