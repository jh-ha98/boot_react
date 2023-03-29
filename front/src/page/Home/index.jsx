/* eslint-disable */
import axios from 'axios';
import React, { useCallback, useEffect, useState } from 'react';
import { Link, Outlet } from 'react-router-dom';
import Loading from '../../component/Loading';
import { DefaultButton } from '../../style/buttons';
import homeImg from "../../resources/img/home.png";
import boardImg from "../../resources/img/board.png";
import menuImg from "../../resources/img/menu.png";
import { Header, LogInButton, LogOutButton, MenuWrap, TopBarWrap, MenuImg, HomeImg, Section, InnerSection, StyledLink, Nav, BoardImg } from './style';

const Home = () => {
  const [member, setMember] = useState();
  const [sideMenu, setSideMenu] = useState(true);

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

  const onClickMenu = useCallback(() => setSideMenu(!sideMenu), [sideMenu]);

  if (member === undefined) {
    return (<Loading />);
  }

  return (
    <>
      <Header>
        <TopBarWrap>
          <MenuWrap>
            <MenuImg src={menuImg} onClick={onClickMenu} />
            <div>logo</div>
          </MenuWrap>
          <div>
            {member === null ?
              <Link to='/user/sign-in'>
                <LogInButton>로그인</LogInButton>
              </Link> : ''}
            {member === null ?
              <Link to='/user/sign-up'>
                <DefaultButton>회원가입</DefaultButton>
              </Link> : ''}
            {!(member === null) ? member.loginId + '님 환영합니다' : ''}
            {!(member === null) ? <LogOutButton onClick={onClickSignOut}>로그아웃</LogOutButton> : ''}
          </div>
        </TopBarWrap>
      </Header>
      <Section>
        <Nav className={sideMenu ? 'show' : 'hide'}>
          <StyledLink to='/'>
            <HomeImg src={homeImg} /> 홈
          </StyledLink>
          <StyledLink to='/board/list'>
            <BoardImg src={boardImg} /> 게시판
          </StyledLink>
        </Nav>
        <InnerSection>
          <Outlet />
        </InnerSection>
      </Section>
    </>
  );
};

export default Home;