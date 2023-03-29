import React, { useRef, useState } from 'react';
import axios from 'axios';
import { Navigate } from 'react-router-dom';
import { Article, CheckIdBtn, Form, IdBox, IdInput, Input, Label, Section, SignUpBtn, Wrap } from './style';

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
    <Article>
      <Section>
        <Wrap>
          <Form>
            <div>
              <Label>ID</Label>
              <IdBox>
                <IdInput ref={loginIdRef} value={loginId} onChange={onChangeLoginId} autoFocus />
                <CheckIdBtn type='button' onClick={onClickCheckId}>중복확인</CheckIdBtn>
              </IdBox>
              <Label>Password</Label>
              <Input ref={passwordRef} type='password' />
              <Label>Email</Label>
              <Input ref={emailRef} type='email' />
            </div>
            <SignUpBtn onClick={onClickSignUp}>회원가입</SignUpBtn>
          </Form>
        </Wrap>
      </Section>
    </Article>
  </>
}

export default SignUp;