import React, { useRef, useState } from 'react';
import axios from 'axios';
import { Navigate } from 'react-router-dom';

const SignUp = () => {
  const [checked, setChecked] = useState(false);
  const [member, setMember] = useState(null);
  const [loginId, setloginId] = useState('');
  const loginIdRef = useRef();
  const passwordRef = useRef();
  const emailRef = useRef();

  const onClickSignUp = (event) => {
    event.preventDefault();

    if (!checked) {
      alert("중복확인 먼저 해주세요.");
      return;
    }

    const param = {
      loginId: loginIdRef.current.value,
      password: passwordRef.current.value,
      email: emailRef.current.value
    };

    console.log(param);

    axios.post('/api/member/sign-up', param)
      .then((res) => {
        const member = res.data.body;
        console.log(res);
        console.log(member);
        alert(res.data.msg);
        setMember(member);
      })
      .catch((error) => {
        console.error(error);
        alert(error.response.data.msg);
      });
  };

  const onClickCheckId = () => {
    const param = {
      loginId: loginIdRef.current.value
    };

    axios.get('/api/member/check-id', { params: param })
      .then((res) => {
        console.log(res);
        setChecked(true);
        alert(res.data.msg);
      })
      .catch((error) => {
        console.error(error);
        alert(error.response.data.msg);
      });
  };

  const onChangeLoginId = (event) => {
    setloginId(event.target.value);
    setChecked(false);
  };

  if (member) {
    return (
      <Navigate to="/user/sign-in" replace={true} />
    );
  }

  return (
    <section id='sign-in-wrap'>
      <div id='sign-in-inner-wrap'>
        <form id='sign-up-form'>
          <div id='input-wrap'>
            <label htmlFor='sign-up-id'>ID</label>
            <div className='id-box'>
              <input id='sign-up-id' ref={loginIdRef} value={loginId} onChange={onChangeLoginId} autoFocus/>
              <input className='button-check-id' type='button' value='중복확인' onClick={onClickCheckId} />
            </div>
            <label>Password</label>
            <input ref={passwordRef} type='password' />
            <label>Email</label>
            <input ref={emailRef} type='email' />
          </div>
          <input type='button' value='회원가입' onClick={onClickSignUp} />
        </form>
      </div>
    </section>


  );
}

export default SignUp;