# **Smart Makeup Web**
## **1. 개요**

### ■ 만든 목적
 이전에 최준원, 한기윤 외 1명이 진행했던 Smart IoT Vanity [프로젝트](https://github.com/zecube/Smart_Makeup) 를 현호성, 최준원, 한기윤이 웹 서버 구현으로 확장시키기 위해 구현했습니다.


### ■ 일정
2024.09.19 ~ 2024.12.02

### ■ 참여자
- 대진대학교 컴퓨터공학과 **현호성 (조장)**
- 대진대학교 휴먼로봇융합전공 **최준원**
- 대진대학교 휴먼로봇융합전공 **한기윤**

## **2. 사용 기술 및 개발 환경**
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


## **3. 내용**

### 구현 기능

## **4. 구현 결과물**

### 회원 가입 기능

### 로그인 기능

### 게시판 글 작성 기능

### 게시판 글 삭제 기능


<!--


레포지토리 리드미 구성
프로젝트 구성
프로젝트 프로그램 설치방법
프로젝트 프로그램 사용법
저작권 및 사용권 정보
프로그래머 정보
버그 및 디버그
참고 및 출처
버전 및 업데이트 정보
FAQ

Table User {
  UserID verchar [pk]
  Password verchar [not null]
  Email verchar [not null]
  PhoneNumber verchar
}

Table Makeup {
  MakeupID int [pk]
  UserID verchar [ref: > User.UserID]
  ColorCode verchar [not null]
  Opacity verchar [not null]
}

Table ProductType {
  MakeupID int [ref: > Makeup.MakeupID]
  ProductCode int [ref: > Product.ProductCode]
  ProductTypeName verchar [not null]
}

Table Product {
  ProductCode int [pk]
  ImageCode int [ref: > ImageLink.ImageCode]
  ProductName verchar [not null]
  Price int [not null]
}

Table Board {
  BoardID int [pk]
  UserID verchar [ref: > User.UserID]
  ImageCode int [ref: > ImageLink.ImageCode]
  Title verchar [not null]
  Content verchar
}

Table Comment {
  CommentID verchar [pk]
  BoardID int [ref: > Board.BoardID]
  UserID verchar [ref: > User.UserID]
  CommentContent verchar [not null]
}

Table ImageLink {
  ImageCode int [pk]
  ImageLink verchar [not null]
}

https://github.com/CJW00113/Smart_Makeup_Archive
-->
