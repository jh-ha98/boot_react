import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import Loading from '../../component/Loading';
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
            {member === null ? <Link to='/user/sign-in'>로그인</Link> : ''}
            {member === null ? <Link to='/user/sign-up'>회원가입</Link> : ''}
            {!(member === null) ? '환영합니다' : ''}
            {!(member === null) ? <button className='button-sign-out' onClick={onClickSignOut}>로그아웃</button> : ''}
          </div>
        </div>
      </header>
      <section className={style.wrap}>
        <nav id='side-bar'>
          <li>
            <Link to='/board/board-list'>게시판 리스트</Link>
          </li>
          <li>
            <Link to='/board/write-form'>게시판 글 작성</Link>
          </li>
        </nav>
        <section className={style['inner-wrap']}>
          <Outlet />
        </section>
      </section>
    </>
  );
};

export default Home;