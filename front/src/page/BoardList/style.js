import styled from 'styled-components';

export const Wrap = styled.div`
width: 100%;
max-width: 80rem;
margin: auto;
`;

export const Serach = styled.div`
width: fit-content;
margin: 0 auto;
margin-right: 0;
padding: 1rem;
`;

export const SerachInput = styled.input`
font-size: medium;
padding: .5rem;
width: 100%;
height: 1.5rem;
background-color: rgb(209, 191, 245);
border: none;
border-radius: .5rem;
&:focus {
  outline: none;
  box-shadow: 0 0 0 .2rem rgb(103, 64, 182);
  transition: all .2s;
}
&::placeholder {
  color: rgb(103, 64, 182);
}`;

export const ListBox = styled.div`
border: .5rem solid rgb(209, 191, 245);
height: 10rem;
margin: 1rem 0;
border-radius: 1rem;
padding: 1rem;
position: relative;
display: flex;
flex-direction: column;
justify-content: space-between;
transition: .2s;
box-shadow: 0 5px 10px rgb(0 0 0 / 20%);
&:hover{
  transform: translateY(-5px);
  box-shadow: 0 10px 10px rgb(0 0 0 / 40%);
}`;

export const DeleteButton = styled.img`
position: absolute;
top: .5rem;
right: .5rem;
width: 1.5rem;
cursor: pointer;
`;

export const Title = styled.div`
margin: .5rem 0;
overflow: hidden;
white-space: nowrap;
text-overflow: ellipsis;
& a { 
  font-size: 2rem;
  text-decoration: none;
  color: rgb(103, 64, 182);
  display: block;
}`;

export const Content = styled.div`
overflow: hidden;
white-space: nowrap;
text-overflow: ellipsis;
color: rgb(103, 64, 182);
`;

export const Info = styled.div`
bottom: .5rem;
left: .5rem;
font-size: small;
opacity: 0.4;
margin: .5rem 0;
`;