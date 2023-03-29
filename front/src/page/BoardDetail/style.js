import styled from 'styled-components';
import { DefaultButton } from "../../style/buttons";

export const Wrap = styled.div`
width: 100%;
max-width: 80rem;
margin: auto;
`;

export const Button = styled(DefaultButton)`
max-width: 5rem;
min-width: 5rem;
font-size: 1.1rem;
margin: 0 0.3rem;
`;

export const TitleWrap = styled.div`
display: flex;
justify-content: space-between;
`;

export const ButtonWrap = styled.div`
display: flex;
justify-content: end;
align-items: center;
padding: 0 1rem;
width: 100%;
margin-top: 0;
margin-right: -1.5rem;
`;

export const InfoWrap = styled.div`
display: flex;
justify-content: space-between;
`;

export const Info = styled.div`
display: block;
`;

export const InfoLoginId = styled.div`
font-size: small;
display: block;
`;

export const InfoTime = styled.div`
font-size: small;
opacity: 0.5;
display: block;
`;

export const CountComment = styled.div`
font-size: small;
display: block;
`;

export const Content = styled.div`
display: block;
margin-top: 1rem;
min-height: 10rem;
`;

export const Hr = styled.hr`
border: 0;
border-top: 1px dashed #8c8c8c;
border-bottom: 1px dashed #fff;
`;

export const CommentBox = styled.div`
flex-direction: column;
`;

export const Textarea = styled.div`
width: 100%;
height: auto;
min-height: 7rem;
line-height: 1.75rem;
resize: none;
font-family: 'Noto Sans KR', sans-serif;
font-size: 1rem;
box-sizing: border-box;
white-space: pre-wrap;
background-color: rgb(209, 191, 245);
border-radius: .5rem;
padding: .5rem;
border: none;
outline: none;
margin: 0 0 0.5rem 0;
&:focus {
  box-shadow: 0 0 0 .1rem rgb(103, 64, 182);
}
&:empty:before {
  display: block;
  content: attr(placeholder);
  color: rgb(103, 64, 182);
}`;