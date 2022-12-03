import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Loading from '../component/Loading';

const Home = () => {
  const [member, setMember] = useState();

  useEffect(() => {
    axios.get('/api/member/info')
      .then((res) => {
        console.log(res.data?.body);
        setMember(res.data?.body);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const onClickSignOut = () => {
    axios.delete('/api/member/sign-out')
      .then((res) => {
        console.log(res.data);
        alert(res.data?.msg);
        setMember(res.data?.body);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  if (member === undefined) {
    return (<Loading />);
  }

  return (
    <>
      <header id='main-header'>
        <div id='top-bar-wrap'>
          <div>logo</div>
          <div>
            {member === null ? <Link to='/user/sign-in'>로그인</Link> : ''}
            {member === null ? <Link to='/user/sign-up'>회원가입</Link> : ''}
            {!(member === null) ? '환영합니다' : ''}
            {!(member === null) ? <button onClick={onClickSignOut}>로그아웃</button> : ''}
          </div>
        </div>
      </header>
      <section id='content-wrap'>
        <nav id='side-bar'>사이드 바</nav>
        <section id='main-content'>메인 콘텐츠</section>
      </section>
    </>
  );
};

export default Home;