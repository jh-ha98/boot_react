import './App.css';
import React from 'react';
import { Route, Routes } from 'react-router-dom';
import SignIn from './page/user/SignIn';
import SignUp from './page/user/SignUp';
import Home from './page/Home';

const App = () => {
  return (
    <Routes>
      <Route path="/">
        <Route index element={<Home />} />
        <Route path="/user/sign-in" element={<SignIn />} />
        <Route path="/user/sign-up" element={<SignUp />} />
      </Route>
    </Routes>
  );
};

export default App;