import React from 'react';
import { Link } from 'react-router-dom';

function Home() {
  return (
    <>
      <header id='main-header'>
        <div id='top-bar-wrap'>
          <div>logo</div>
          <div>
            <Link to='/user/sign-up'>회원가입</Link>
            &nbsp;|&nbsp;
            <Link to='/user/sign-in'>로그인</Link>
          </div>
        </div>
      </header>
      <section id='content-wrap'>
        <nav id='side-bar'>사이드 바</nav>
        <section id='main-content'>메인 콘텐츠</section>
      </section>
    </>
  );
}

export default Home;