import './App.css';
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import SignIn from './page/SignIn';
import SignUp from './page/SignUp';
import Home from './page/Home';
import BoardWrite from './page/BoardWrite';
import BoardList from './page/BoardList';
import BoardDetail from './page/BoardDetail';
import BoardUpdate from './page/BoardUpdate';

const App = () => {
  return (
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
  );
};

export default App;