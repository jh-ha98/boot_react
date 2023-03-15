import React, { useState, useRef } from 'react';
import axios from 'axios';
import { Navigate } from 'react-router-dom';

const SignIn = () => {
  const [member, setMember] = useState(null);
  const loginIdRef = useRef();
  const passwordRef = useRef();

  const onClickLogin = (event) => {
    event.preventDefault();

    const param = {
      loginId: loginIdRef.current.value,
      password: passwordRef.current.value
    };

    axios.post('/api/member/sign-in', param)
      .then((res) => {
        const member = res.data.body;
        console.log(res);
        alert(res.data.msg);
        setMember(member);
      })
      .catch((err) => {
        console.log(err);
        alert(err.response.data.msg);
      });
  };

  if (member) {
    return (
      <Navigate to="/" replace={true} />
    );
  }

  return (
    <section id='sign-in-wrap'>
      <div id='sign-in-inner-wrap'>
        <form id='sign-in-form'>
          <div id='input-wrap'>
            <label htmlFor='login-id'>ID</label>
            <input id='login-id' ref={loginIdRef} autoFocus />
            <label>Password</label>
            <input type='password' ref={passwordRef} />
          </div>
          <button onClick={onClickLogin}>로그인</button>
        </form>
      </div>
    </section>
  );
};

export default SignIn;