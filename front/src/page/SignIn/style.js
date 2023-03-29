import styled from 'styled-components';

export const Article = styled.article`
width: 100%;
height: 100%;
display: flex;
justify-content: center;
flex-direction: column;
`;

export const H2 = styled.h2`
font-size: 2rem;
`;

export const Section = styled.section`
display: flex;
flex-direction: column;
justify-content: center;
height: 100%;
padding: .5rem;
background-color: rgb(210, 192, 247);
`;

export const DivWrap = styled.div`
display: flex;
justify-content: center;
`;

export const Form = styled.form`
height: 20rem;
padding: 2rem;
width: 25rem;
background-color: mediumpurple;
border-radius: 1rem;
display: flex;
flex-direction: column;
justify-content: space-between;
box-shadow: 0 14px 28px rgba(0, 0, 0, .4);
`;

export const Label = styled.label`
margin: 0;
padding: .3rem;
display: block;
font-weight: bolder;
color: rgb(209, 191, 245);
`;

export const Input = styled.input`
display: block;
width: 100%;
box-sizing: border-box;
padding: .5rem;
border-radius: .5rem;
border: 0;
`;

export const SignInBtn = styled.button`
width: 100%;
padding: .5rem;
border-radius: 1rem;
border: 0;
background-color: rgb(209, 191, 245);
color: rgb(103, 64, 182);
font-weight: bolder;
font-size: 2rem;
box-shadow: 0 .4rem rgb(103, 64, 182);
transition: all .2s;
&:hover{
  box-shadow: 0 0 rgb(103, 64, 182);
  transform: translateY(.4rem);
  cursor: pointer;
}`;