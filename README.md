# MAG
`Make A General`의 약자로 해당 프로젝트를 진행하며 스스로 발전하여 자신을 장군님으로 만들어보자는 취지로 만들어진 프로젝트입니다.

## 목차
- [Spring boot](#spring-boot)
- [React](#React)
- [학습내용 Spring](./docs/learning-spring.md)
- [학습내용 React](./docs/learning.md)

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

## 배포
### 서버 구성도
Nginx를 통해 Front-End 서버와 API 서버를 하나의 서버처럼 동작하도록 구성합니다.  
SSL 설정을 하고 http 요청을 https 요청으로 리다이렉트 시킵니다.  
<img src='./docs/server-structure.png'  width='30%' height='30%'>

### Nginx

### Proxy Server

### SSL