# MAG
`Make A General`의 약자로 해당 프로젝트를 진행하며 스스로 발전하여 자신을 장군님으로 만들어보자는 취지로 만들어진 프로젝트입니다.
- [학습내용 Spring](./docs/learning-spring.md)
- [학습내용 React](./docs/learning-react.md)
- [학습내용 배포](./docs/learning-release.md)

## 목차
- [Spring boot](#spring-boot)
- [React](#react)
- [서버 구성도](#서버-구성도)
- [소스 배포시 명령어](#소스-배포시-명령어)

## Spring Boot
API 서버로서 Spring 프레임워크를 사용했습니다.

### 프로젝트 구조
```
api/src
├─ main
│   ├─ main
│   │   ├─ java.hjh.api.controller
│   │   │   └─ controller...
│   │   ├─ java.hjh.api.service
│   │   │   └─ service...
│   │   ├─ java.hjh.api.repository
│   │   │   └─ repository...
│   │   ├─ java.hjh.api.domain.entity
│   │   │   └─ entity...
│   │   ├─ java.hjh.api.domain.dto
│   │   │   └─ dto...
│   │   └─ java.hjh.api.domain.type
│   │       └─ type...
│   └─ resources
│       ├─ application-local.properties
│       ├─ application-prod.properties
│       └─ application.properties
└─ test
    └─ 테스트 코드...
```

## React
Front-End 서버로서 Spring 프레임워크를 사용했습니다.

### 프로젝트 구조
```
front
├─ public
├─ src
│   ├─ component
│   │   └─ component...
│   ├─ page
│   │   └─ page...
│   ├─ style
│   │   └─ style...
│   ├─ resources
│   │   └─ img
│   │       └─ 이미지...
│   ├─ App.jsx
│   ├─ App.css
│   └─ index.jsx
└─ package.json
```

## 서버 구성도
<img src='./docs/server-structure.png'  width='50%' height='50%'>

## 소스 배포시 명령어
```bash
# Spring 프로젝트 빌드
./gradlew build
# 빌드프로젝트 실행
java -jar -Dspring.profiles.active=prod [파일명].jar &
# 작성된 react 코드를 build한 파일 (html, css, js, img...)
npm run build
# 자바스크립트로 작성된 js파일을 실행하는 명령어
node server.js &
```