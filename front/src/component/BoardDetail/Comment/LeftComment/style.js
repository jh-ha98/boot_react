import styled from 'styled-components';

export const LeftWrap = styled.div`
display: flex;
margin: 1rem 0;
`;

export const CommentBox = styled.div`
position: relative;
font-size: 1rem;
line-height: 1.5rem;
min-width: 15rem;
max-width: fit-content;
background: #c4f0db;
border-radius: 2rem;
padding: 1rem;
color: #000;
margin: 0;
`;

export const TextComment = styled.pre`
overflow-wrap: break-word;
width: 100%;
line-height: 1.5rem;
height: auto;
min-height: 5rem;
resize: none;
white-space: pre-wrap;
margin: 0;
`;

export const UpdateComment = styled.span`
overflow-wrap: break-word;
width: 100%;
line-height: 1.5rem;
min-height: 6rem;
resize: none;
white-space: pre-wrap;
background-color: white;
display: block;
border-radius: 1rem;
padding: .5rem;
box-sizing: border-box;
`;

export const ImgButton = styled.img`
align-self: flex-end;
width: 1rem;
cursor: pointer;
`;

export const UpdateButton = styled.button`
align-self: flex-end;
margin: 0 .2rem;
`;

export const TimeStr = styled.div`
font-size: small;
opacity: 0.5;
position: relative;
`;