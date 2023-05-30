import styled from 'styled-components';

export const Article = styled.article`
width: 100%;
height: 100%;
/* 컨테이너 공간 맞추기 */
display: flex;
justify-content: center;

/*flex-direction : row (기본값, 가로가 중심축)  */
/*flex-direction : column (세로가 중심축, 위에서 아래로)  */
flex-direction: column;
`;

export const Section = styled.section`
display: flex;
flex-direction: column;
justify-content: center;
height: 100%;
padding: .5rem;
background-color: rgb(210, 192, 247);
`;

export const Wrap = styled.div`
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

export const IdBox = styled.div`
display: flex;
justify-content: space-between;
`;

export const IdInput = styled.input`
display: block;
width: 100%;
box-sizing: border-box;
padding: .5rem;
border-radius: .5rem;
border: 0;
flex: .8;
`;

export const CheckIdBtn = styled.button`
border: 0;
flex: .18;
background-color: rgb(209, 191, 245);
color: rgb(103, 64, 182);
font-weight: bolder;
box-shadow: 0 0 0 0 rgb(103, 64, 182);
transition: all .2s;
border-radius: .5rem;
&:hover {
  box-shadow: 0 0 0 .2rem rgb(103, 64, 182);
}
`;

export const Input = styled.input`
display: block;
width: 100%;
box-sizing: border-box;
padding: .5rem;
border-radius: .5rem;
border: 0;
`;

export const SignUpBtn = styled.button`
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
}
`;