import styled from 'styled-components';

export const DefaultButton  = styled.button`
box-sizing: border-box;
padding: .5rem;
border-radius: .5rem;
border: 0;
flex: .18;
background-color: rgb(209, 191, 245);
color: rgb(103, 64, 182);
font-weight: bolder;
box-shadow: 0 0 0 0 rgb(103, 64, 182);
transition: all .2s;
&:hover{
  box-shadow: 0 0 0 .2rem rgb(103, 64, 182);
}
`;