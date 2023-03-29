import styled from 'styled-components';
import { DefaultButton } from '../../style/buttons';

export const Article = styled.article`
width: 100%;
max-width: 80rem;
margin: auto;
`;

export const Header = styled.header`
display: flex;
justify-content: space-between;
align-items: center;
position: sticky;
top: -1rem;
background-color: white;
padding: 0 1rem;
width: 100%;
margin: 0 -1rem;
`;

export const HeaderTitle = styled.h2`
color: rgb(103, 64, 182);
`;

export const WriteButton = styled(DefaultButton)`
max-width: 5rem;
min-width: 5rem;
font-size: 1.1rem;
`;

export const Title = styled.input`
display: block;
width: 100%;
box-sizing: border-box;
height: 3rem;
margin: 1rem 0;
background-color: rgb(209, 191, 245);
border: none;
border-radius: .5rem;
padding: .5rem;
font-size: 1rem;
outline: none;
&::placeholder{
  color: rgb(103, 64, 182);
}
&:focus{
  box-shadow: 0 0 0 .1rem rgb(103, 64, 182);
}`;

export const Content = styled.span`
display: block;
width: 100%;
box-sizing: border-box;
height: 3rem;
margin: 1rem 0;
background-color: rgb(209, 191, 245);
border: none;
border-radius: .5rem;
padding: .5rem;
font-size: 1rem;
outline: none;
height: auto;
min-height: 30rem;
&:focus{
  box-shadow: 0 0 0 .1rem rgb(103, 64, 182);
}
&:empty::before{
  display: block;
  content: attr(placeholder);
  color: rgb(103, 64, 182);
}`;