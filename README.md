# Smart Makeup Web
## 1. 개요

### ■ 만든 목적
 이전에 최준원, 한기윤 외 1명이 진행했던 Smart IoT Vanity [프로젝트](https://github.com/zecube/Smart_Makeup) 를 현호성, 최준원, 한기윤이 웹 서버 구현으로 확장시키기 위해 구현했습니다.


### ■ 일정
2024.09.19 ~ 2024.12.02

### ■ 참여자
- 대진대학교 컴퓨터공학과 **현호성**
- 대진대학교 휴먼로봇융합전공 **최준원**
- 대진대학교 휴먼로봇융합전공 **한기윤**

## 2. 사용 기술 및 개발 환경
- **Language** :
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Python](https://img.shields.io/badge/python-3670A0?style=for-the-badge&logo=python&logoColor=ffdd54)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-%23005C0F.svg?style=for-the-badge&logo=Thymeleaf&logoColor=white)

- **Library** :
![OpenCV](https://img.shields.io/badge/opencv-%23white.svg?style=for-the-badge&logo=opencv&logoColor=white)
![scikit-learn](https://img.shields.io/badge/scikit--learn-%23F7931E.svg?style=for-the-badge&logo=scikit-learn&logoColor=white)
![Pandas](https://img.shields.io/badge/pandas-%23150458.svg?style=for-the-badge&logo=pandas&logoColor=white)
![Matplotlib](https://img.shields.io/badge/Matplotlib-%23ffffff.svg?style=for-the-badge&logo=Matplotlib&logoColor=black)
![Keras](https://img.shields.io/badge/Keras-%23D00000.svg?style=for-the-badge&logo=Keras&logoColor=white)
![MediaPipe](https://img.shields.io/badge/MediaPipe-%23150458.svg?style=for-the-badge&logo=MediaPipe&logoColor=white)

- **Framework** :
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Bootstrap](https://img.shields.io/badge/bootstrap-%238511FA.svg?style=for-the-badge&logo=bootstrap&logoColor=white)

- **Database** :
![MySQL](https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white)

- **운영체제** :
![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white)

- **Version Control** :
![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white)


## 3. 내용

### 구현 기능
#
### 데이터베이스 구현
![캡처](https://github.com/user-attachments/assets/46aa523b-6104-4259-b040-0c2bdafbbe25)
MySQL로 작성하였습니다.

### 메인 페이지
![11 (2)](https://github.com/user-attachments/assets/0c2ed386-b9fe-4c62-a68a-346cfe744447)

### 회원 가입 기능
![그림4 (2)](https://github.com/user-attachments/assets/70478c2f-840b-46b1-9f99-c63c6abec768)

### 로그인 기능
![그림3 (2)](https://github.com/user-attachments/assets/deee3f67-b335-4313-939f-da6522d1f371)

### 게시판
![그림2 (2)](https://github.com/user-attachments/assets/fc35b9b2-e91a-41e2-8066-f343e869e662)
![그림1 (2)](https://github.com/user-attachments/assets/45ffdfe3-80c9-4965-9445-4b1b7ee3e886)

### 제품 추천하기
![22 (2)](https://github.com/user-attachments/assets/354c8212-2c4f-48d3-bfe2-3826e4663245)

<!--
본 e2b06f
내추럴 스웨이드 d8a665
던 c59c63
에크루 fac7a7
웜 크림 c88d4f


Table member {
  member_id varchar(50) [pk, unique]
  member_password varchar(100)
  email varchar(50)
  phone varchar(20)
}

Table product_category {
  category varchar(50) [pk, unique]
}

Table makeup {
  makeup_id bigint [pk, increment]
  member_id varchar(50)
  category varchar(50)
  button_number int
  color_code varchar(20)
  opacity int
}

Table board {
  board_id bigint [pk, increment]
  member_id varchar(50)
  title varchar(50)
  content_text text
}

Table product {
  product_code bigint [pk, increment]
  product_name varchar(50)
  category varchar(50)
  Price int
}

Table image {
  image_code bigint [pk, increment]
  board_id bigint
  product_code bigint
  image_link varchar(100)
}

Table comment {
  comment_id bigint [pk, increment]
  board_id bigint
  member_id varchar(50)
  comment_content text
}

Ref: makeup.member_id > member.member_id
Ref: makeup.category > product_category.category

Ref: board.member_id > member.member_id

Ref: product.category > product_category.category

Ref: image.board_id > board.board_id
Ref: image.product_code > product.product_code

Ref: comment.board_id > board.board_id
Ref: comment.member_id > member.member_id


### **1. 주요 구성 요소**

1. **메인 서버**:
    - 기술 스택: Spring Boot, Spring Security
    - 주요 기능:
        - 홈페이지 소개 및 사용법 제공
        - 로그인/회원가입 기능
        - 사용자 데이터 저장 및 관리 (DB 연동)
        - 사용자 선택 UI 제공 및 처리
        - HTML 기반 사용자 인터페이스 제공
2. **서브 서버**:
    - 기술 스택: OpenAPI, 영상 처리 모듈(OpenCV)
    - 주요 기능:
        - 실시간 영상 처리 (얼굴 인식 및 화장 효과)
        - 메인 서버와의 소켓 통신
        - 사용자 입력 파라미터에 따른 이미지 연산
3. **데이터베이스(DB)**:
    - 기술 스택: MySQL
    - 메인 서버와 연동
    - 데이터 저장 항목:
        - 사용자 계정 정보 (ID, 비밀번호, 이메일, 전화번호)
        - 사용자 설정 정보 (스킨/입술 색상 및 투명도)
        - 화장품 제품 정보
        - 게시판 데이터 (게시글, 댓글 등)
4. **사용자 인터페이스(UI)**:
    - 기술 스택: HTML, CSS, JavaScript
    - 주요 기능:
        - 실시간 영상 스트리밍 표시
        - 화장 색상 및 투명도 설정 UI
        - 게시판 및 제품 정보 표시

---

### **2. 데이터 흐름**

### **A. 로그인 및 사용자 데이터 관리**

1. 사용자는 메인 서버의 로그인 페이지에서 로그인 시도.
2. 메인 서버는 Spring Security로 인증 후 데이터베이스에서 사용자 정보를 확인.
3. 인증 성공 시, 사용자 정보에 따라 개인화된 데이터(예: 저장된 화장 설정)를 메인 서버가 읽어 제공.

### **B. 실시간 화장 효과**

1. 사용자가 "화장하기" 버튼 클릭.
2. 메인 서버는 서브 서버와 소켓 통신을 시작.
3. 서브 서버는 사용자의 얼굴을 캠으로 읽어 실시간으로 영상 처리.
    - 영상 데이터를 JPG로 쪼개서 메인 서버로 전송.
4. 메인 서버는 받은 JPG 데이터를 HTML UI에 실시간으로 렌더링해 사용자에게 표시.
5. 사용자가 선택한 화장 색상/투명도는 POST 요청을 통해 서브 서버로 전달.
6. 서브 서버는 해당 매개변수로 영상 처리 로직을 수정해 실시간으로 반영.

### **C. 화장 제품 정보 표시**

1. 사용자가 "화장 제품" 버튼 클릭.
2. 메인 서버는 데이터베이스에서 제품 정보 테이블을 읽어 사용자에게 제품 목록 제공.
3. 로그인한 사용자라면, 저장된 화장 색상 정보와 연관된 제품에 강조(border) 표시.

### **D. 게시판 기능**

1. 사용자가 "화장품 톡톡" 버튼 클릭.
2. 메인 서버는 데이터베이스에서 게시판 테이블을 읽어 게시판 페이지 렌더링.
3. 로그인한 사용자는 게시글 작성 및 댓글 작성 가능.
4. 작성된 게시글/댓글은 메인 서버를 통해 데이터베이스에 저장.

---

### **3. 시스템 설계 다이어그램**

```
+--------------------+       +---------------------+
|     User (UI)      |<----->|    Main Server      |<----------------------+
| (HTML, CSS, JS)    |       | (Spring Boot)       |                       |
+--------------------+       +---------------------+                       |
       ^                           |                                       |
       |                           v                                       |
       |                   +----------------+                              |
       |                   |  Database (DB) |                              |
       |                   +----------------+                              |
       |                           |                                       |
       |                           v                                       |
       |                  +--------------------+                           |
       +------------------|   Sub Server       |                           |
                          | (OpenAPI, 영상처리) |<--------------------------+
                          +--------------------+

```

---

### **4. 시스템 특징**

1. **모듈화된 설계**: 메인 서버와 서브 서버가 분리되어 기능별로 독립적으로 작동.
2. **확장성**: 서브 서버의 영상 처리 로직을 추가/변경해 새로운 기능 구현 가능.
3. **실시간 데이터 흐름**: 소켓 통신을 통해 실시간 데이터 전송 및 반영.
4. **보안**: Spring Security로 사용자 인증 및 데이터 보호.
5. **사용자 친화성**: HTML 기반의 직관적 인터페이스 제공.

---

이 구조를 바탕으로 시스템 아키텍처를 문서화하거나 시각화(예: 다이어그램 도구)하면 프로젝트 전반의 가독성과 유지보수성을 높일 수 있습니다! 😊
-->
