# Front-End 학습 정리
React를 이용한 사용자 인테페이스를 만들면서 정리한 학습내용입니다.
- [메인](../README.md)

## 목차
- [Javascript](#javascript)
- [React](#react)
    - [사용된 패키지](#사용된-패키지)
    - [명령어](#명령어)
    - [스타일](#스타일)
    - [전개구문 사용하기](#전개구문-사용하기)
    - [라우터 만들기](#라우터-만들기)
    - [SWR 사용하기](#swr-사용하기)

## Javascript
JavaScript는 웹 페이지에서 복잡한 기능을 구현할 수 있도록 하는 스크립팅 언어 또는 프로그래밍 언어입니다.
- `Event.preventDefault()`: 어떤 이벤트에대해 기본 동작으로 실행하는 이벤트를 실행하지 않도록 지정합니다.

## React
사용자 인터페이스를 만들기 위한 JavaScript 라이브러리.  
해당 프로젝트에서는 CRA(Create React App)를 사용하였습니다.
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

### 스타일
styled-components
- styled-components 설치하기 ( cd .\front\ )
    ```css
    npm i styled-components
    ```
- 사용 예시
    ```css
    import styled from "styled-components";

    //스타일 설정
    const PrimaryStyledButton = styled(StyledButton)`
    background-color: pink;
    color: black;
    `;

    //적용
    function App() {
    return (
        <div className="App">
            <PrimaryStyledButton>버튼</PrimaryStyledButton>
        </div>
        );
    }
    ```
- 전역스타일 설정
    - APP.jsx
    ```css
    import styled, { createGlobalStyle } from "styled-components";

    //전역스타일 지정
    const GlobalStyle = createGlobalStyle`
    html,body {
    margin: 0;
    padding: 0;
    width: 100vw;
    height: 100vh;
    }
    #root {
    height: 100vh; 
    width: 100vw;

    /* 컨테이너 공간 맞추기 */
    display: flex;

    /*flex-direction : row (기본값, 가로가 중심축)  */
    /*flex-direction : column (세로가 중심축, 위에서 아래로)  */
    flex-direction: column;
    }
    button {
    cursor: pointer;
    }
    h2 {
    font-size: 2rem;
    }`;
    const App= () => {
    return <>
        <GlobalStyle />
        <Routes>
            <Route path="/user/sign-in" element={<SignIn />} />
            <Route path="/user/sign-up" element={<SignUp />} />
        </Routes>
    </>
    }
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
### SWR 사용하기
SWR?
- 데이터를 가져오기 위한 React Hooks
- Stale-While-Revalidate의 줄임말로 백그라운드에서 캐시를 재검증(revalidate)하는 동안에 기존의 캐시 데이터(stale)를 사용하여 화면을 그려줍니다.
- 도중에 에러를 반환하더라도 캐시된 데이터를 활용할 수 있게 함으로써 불필요한 데이터 호출과 렌더링에 시간을 쓰지 않고 효율적으로 동작할 수 있습니다.

기본 사용방법
```jsx
import useSWR from 'swr'
 
function Profile () {
  const { data, error, isLoading } = useSWR('/api/user/123', fetcher)
 
  if (error) return <div>failed to load</div>
  if (isLoading) return <div>loading...</div>
 
  // 데이터 렌더링
  return <div>hello {data.name}!</div>
}
```
API 기본구성
```jsx
const { data, error, isLoading, isValidating, mutate } = useSWR(key, fetcher, options)
```
- key: 요청을 위한 고유한 키 문자열(또는 함수 / 배열 / null)
- fetcher: (옵션) 데이터를 가져오기 위한 함수를 반환하는 Promise
- options: (옵션) SWR hook을 위한 옵션 객체

데이터 가져오기
```jsx
import fetch from 'unfetch'
 
const fetcher = url => fetch(url).then(r => r.json())
 
function App () {
  const { data, error } = useSWR('/api/data', fetcher)
  // ...
}
```

Axios
```jsx
import axios from 'axios'
 
const fetcher = url => axios.get(url).then(res => res.data)
 
function App () {
  const { data, error } = useSWR('/api/data', fetcher)
  // ...
}
```