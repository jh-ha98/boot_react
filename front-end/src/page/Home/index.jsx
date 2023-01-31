import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import Loading from '../../component/Loading';
import buttonStyle from "../../style/buttons.module.css";
import style from './style.module.css';

const Home = () => {
  const [member, setMember] = useState();

  useEffect(() => {
    axios.get('/api/member/info')
      .catch((error) => error.response)
      .then((res) => {
        console.log(res.data?.body);
        setMember(res.data?.body);
      })
      .catch((error) => console.log(error));
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
            {member === null ?
              <Link to='/user/sign-in'>
                <button className={`${buttonStyle['default-button']} ${style.login}`}>로그인</button>
              </Link> : ''}
            {member === null ?
              <Link to='/user/sign-up'>
                <button className={buttonStyle['default-button']}>회원가입</button>
              </Link> : ''}
            {!(member === null) ? member.loginId + '님 환영합니다' : ''}
            {!(member === null) ? <button className={buttonStyle['default-button']} onClick={onClickSignOut}>로그아웃</button> : ''}
          </div>
        </div>
      </header>
      <section className={style.wrap}>
        <nav className={style['side-bar']}>
          <Link to='/' className={style['side-link']}>홈</Link>
          <Link to='/board/list' className={style['side-link']}>게시판 리스트</Link>
        </nav>
        <section className={style['inner-wrap']}>
          <Outlet />
        </section>
      </section>
    </>
  );
};

export default Home;