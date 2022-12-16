import './App.css';
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import SignIn from './page/user/SignIn';
import SignUp from './page/user/SignUp';
import Home from './page/Home';
import WriteForm from './page/board/WriteForm';
import BoardList from './page/board/BoardList';

const App = () => {
  return (
    <Routes>
      <Route path="/" element={<Home />} >
        <Route path="/board/board-list" element={<BoardList />} />
        <Route path="/board/write-form" element={<WriteForm />} />
      </Route>
      <Route path="/user/sign-in" element={<SignIn />} />
      <Route path="/user/sign-up" element={<SignUp />} />
    </Routes>
  );
};

export default App;