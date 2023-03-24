import React, { useRef, useState } from 'react';
import axios from 'axios';
import { Navigate } from 'react-router-dom';
import style from './style.module.css';
import { Secsion, Wrap } from './style';

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

    axios.post('/api/member/sign-up', param)
      .then((res) => {
        const member = res.data.body;
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

  return <>
    <article className={style.article}>
      <Secsion>
        <Wrap>
          <form className={style[`sign-up-form`]}>
            <div id='input-wrap'>
              <label className={style[`sign-up-label`]}>ID</label>
              <div className={style[`id-box`]}>
                <input className={style[`sign-up-id`]} ref={loginIdRef} value={loginId} onChange={onChangeLoginId} autoFocus />
                <button type='button' className={style[`check-id-button`]} onClick={onClickCheckId}>중복확인</button>
              </div>
              <label className={style[`sign-up-label`]}>Password</label>
              <input className={style[`sign-up-input`]} ref={passwordRef} type='password' />
              <label className={style[`sign-up-label`]}>Email</label>
              <input className={style[`sign-up-input`]} ref={emailRef} type='email' />
            </div>
            <button className={style[`sign-up-button`]} onClick={onClickSignUp}>회원가입</button>
          </form>
        </Wrap>
      </Secsion>
    </article>
  </>
}

export default SignUp;