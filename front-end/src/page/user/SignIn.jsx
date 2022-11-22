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
        console.log(member)
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
    <form>
      <p>id: <input ref={loginIdRef} /></p>
      <p>password: <input type='password' ref={passwordRef} /></p>
      <button type='buutton' onClick={onClickLogin}>로그인</button>
    </form>
  );
};

export default SignIn;