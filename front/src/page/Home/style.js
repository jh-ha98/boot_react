import styled from 'styled-components';
import { Link } from 'react-router-dom';
import { DefaultButton } from '../../style/buttons';

export const Header = styled.header`
background-color: mediumpurple;
display: flex;
height: 3rem;

/* 중심축에서 아이템 배치 */
justify-content: center;
flex-direction: column;
color: whitesmoke;
& a {
  color: whitesmoke;
}`;

export const TopBarWrap = styled.div`
display: flex;

/* 아이템 사이에 간격 맞추기 */
justify-content: space-between;
margin: 1rem;
`;

export const MenuWrap = styled.div`
display: flex;
align-items: center;
`;

export const HomeImg = styled.img`
width: 1.4rem;
height: 1.4rem;
vertical-align: text-bottom;
margin-right: 1.5rem;
`;

export const MenuImg = styled.img`
width: 1.4rem;
height: 1.4rem;
margin-right: 1.5rem;
cursor: pointer;
`;

export const BoardImg = styled.img`
width: 1.4rem;
height: 1.4rem;
vertical-align: text-bottom;
margin-right: 1.5rem;
`;

export const LogInButton = styled(DefaultButton)`
margin: 0 0.3rem;
`;

export const LogOutButton = styled(DefaultButton)`
margin-left: .3rem;
`;

export const Section = styled.section`
flex: 1;
display: flex;
overflow: hidden;
`;

export const InnerSection = styled.section`
flex: 1;
overflow: auto;
padding: .7rem;
`;

export const StyledLink = styled(Link)`
padding: 1rem;
font-weight: bolder;
text-decoration-line: none;
display: block;
&:hover{
  background-color: white;
}`;

export const Nav = styled.nav`
background-color: rgb(177, 149, 233);
white-space: nowrap;
overflow: hidden;
height: 100%;
&.show{
  background-color: rgb(177, 149, 233);
  width: 15rem;
}
&.hide{
  background-color: rgb(177, 149, 233);
  width: calc(1.4rem + 2 * 0.1rem + 2 * 1rem);
  white-space: nowrap;
  overflow-x: hidden;
}`;