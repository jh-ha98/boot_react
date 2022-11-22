import React, { useRef } from 'react';
import axios from 'axios';

function SignUp() {
  const loginIdRef = useRef();
  const passwordRef = useRef();
  const emailRef = useRef();

  const onClickSignUp = () => {
    const param = {
      loginId: loginIdRef.current.value,
      password: passwordRef.current.value,
      email: emailRef.current.value
    };

    console.log(param);

    axios.post('/api/member/sign-up', param)
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const onClickCheckId = () => {
    const param = {
      loginId: loginIdRef.current.value
    };

    axios.get('/api/member/check-id', { params: param })
      .then((res) => {
        console.log(res);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <form>
      <p>
        id: <input ref={loginIdRef} />
        <input type='button' value='중복확인' onClick={onClickCheckId} />
      </p>
      <p>password: <input ref={passwordRef} type='password' /></p>
      <p>email: <input ref={emailRef} type='email' /></p>
      <input type='button' value='회원가입' onClick={onClickSignUp} />
    </form>
  );
}

export default SignUp;