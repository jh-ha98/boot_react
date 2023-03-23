import { createGlobalStyle } from 'styled-components';
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import SignIn from './page/SignIn';
import SignUp from './page/SignUp';
import Home from './page/Home';
import BoardWrite from './page/BoardWrite';
import BoardList from './page/BoardList';
import BoardDetail from './page/BoardDetail';
import BoardUpdate from './page/BoardUpdate';

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

const App = () => {
  return <>
    <GlobalStyle />
    <Routes>
      <Route path="/" element={<Home />} >
        <Route path="/board/list" element={<BoardList />} />
        <Route path="/board/list/:boardId" element={<BoardDetail />} />
        <Route path="/board/write" element={<BoardWrite />} />
        <Route path="/board/update/:boardId" element={<BoardUpdate />} />
      </Route>
      <Route path="/user/sign-in" element={<SignIn />} />
      <Route path="/user/sign-up" element={<SignUp />} />
    </Routes>
  </>
};

export default App;