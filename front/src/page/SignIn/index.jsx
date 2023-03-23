import React, { useState, useRef } from 'react';
import axios from 'axios';
import { Navigate } from 'react-router-dom';
import style from './style.module.css';

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

  return <>
    <article className={style.article}>
      <section className={style[`sign-in-wrap`]}>
        <div className={style[`sign-in-inner-wrap`]}>
          <form className={style[`sign-in-form`]}>
            <div id='input-wrap'>
              <label className={style[`sign-in-label`]}>ID</label>
              <input className={style[`sign-in-input`]} ref={loginIdRef} autoFocus />
              <label className={style[`sign-in-label`]}>Password</label>
              <input className={style[`sign-in-input`]} type='password' ref={passwordRef} />
            </div>
            <button className={style[`sign-in-button`]} onClick={onClickLogin}>로그인</button>
          </form>
        </div>
      </section>
    </article>
  </>;
};

export default SignIn;