import React, { useState, useRef } from 'react';
import axios from 'axios';
import { Navigate } from 'react-router-dom';
import { Article, DivWrap, Form, Input, Label, Section, SignInBtn } from './style';

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
        console.error(err);
        alert(err.response.data.msg);
      });
  };

  if (member) {
    return (
      <Navigate to="/" replace={true} />
    );
  }

  return <>
    <Article>
      <Section>
        <DivWrap>
          <Form>
            <div>
              <Label>ID</Label>
              <Input ref={loginIdRef} autoFocus />
              <Label>Password</Label>
              <Input type='password' ref={passwordRef} />
            </div>
            <SignInBtn onClick={onClickLogin}>로그인</SignInBtn>
          </Form>
        </DivWrap>
      </Section>
    </Article>
  </>;
};

export default SignIn;