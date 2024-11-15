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



-->
