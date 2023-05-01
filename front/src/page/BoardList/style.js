import styled from 'styled-components';
import { DefaultButton } from '../../style/buttons';

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

export const PageButton = styled(DefaultButton)`
width: 100%;
&:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}`;