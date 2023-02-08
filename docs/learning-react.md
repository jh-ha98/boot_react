# Front-End 학습 정리
React를 이용한 사용자 인테페이스를 만들면서 정리한 학습내용입니다.
- [메인](../README.md)

## 목차
- [Javascript](#javascript)
- [React](#react)
    - [사용된 패키지](#사용된-패키지)
    - [명령어](#명령어)
    - [전개구문 사용하기](#전개구문-사용하기)
    - [라우터 만들기](#라우터-만들기)

## Javascript
JavaScript는 웹 페이지에서 복잡한 기능을 구현할 수 있도록 하는 스크립팅 언어 또는 프로그래밍 언어입니다.
- `Event.preventDefault()`: 어떤 이벤트에대해 기본 동작으로 실행하는 이벤트를 실행하지 않도록 지정합니다.

## React
사용자 인터페이스를 만들기 위한 JavaScript 라이브러리.  
해당 프로젝트에서는 CRA(Create React App)를 사용하였다.
### 사용된 패키지
- `axios: ^1.1.3`
    - 서버측에 http 비동기 요청을 보내기위한 패키지.
- `react: ^18.2.0`
    - react를 사용하기위한 패키지
- `react-dom: ^18.2.0`
    - jsx형태의 Javascript내 html 태그 문법을 사용하기위한 패키지
- `react-router-dom: ^6.4.3`
    - 페이지 이동을 위한 라우팅 패키지
- `express: ^4.18.2`
    - react로 빌드한 결과를 배포하기위한 node 서버 프레임워크

### 명령어
```bash
# 개발환경 프론트 서버 시작 명령어
npm run start
# 작성된 react 코드를 build한 파일 (html, css, js, img...)
npm run build
# 자바스크립트로 작성된 js파일을 실행하는 명령어
node [파일명].js
```

### 전개구문 사용하기
react를 사용할때 필수 개념인 데이터 변경감지의 예제 코드입니다.
```jsx
// 예제 코드
const onClickComment = () => {
    const param = {
        boardId: params.boardId,
        comment: commentRef.current.value,
    };

axios.post('/api/comment/write', null, { params: param })
    .then((res) => {
        console.log(res);
        const comment = res.data.body;

        // const newData = {};
        // newData.boardId = boardDetail.boardId;
        // newData.title = boardDetail.title;
        // newData.content = boardDetail.content;
        // newData.memberId = boardDetail.memberId;
        // newData.email = boardDetail.email;
        // newData.createTime = boardDetail.createTime;
        // newData.createTimeStr = boardDetail.createTimeStr;
        // newData.comments = [];

        // for (const comment of boardDetail.comments)
        //   newData.comments.push(comment);

        // newData.comments.push(comment);

        // ↓   ↓

        // solution 전개구문 사용하기
        const newData = {
            ...boardDetail,
            comments: [
                ...boardDetail.comments,
                comment
            ]
        };

        setBoardDetail(newData);
        alert(res.data.msg);
    })
    .catch((err) => {
        console.error(err);
        alert(err.response.data.msg);
    });
};
```

### 라우터 만들기
react-router를 사용하는 예제 코드입니다.
```jsx
import { createBrowserRouter, Route, RouterProvider, Routes } from 'react-router-dom';

const router = createBrowserRouter([
    {
        path: '/',
        element: <Home />,
    },
    {
        path: '/user/sign-up',
        element: <SignUp />,
    },
]);

const App = () => {
    return(
        <RouterProvider router={router} />
    )
}
```