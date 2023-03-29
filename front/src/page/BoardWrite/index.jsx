import axios from 'axios';
import React from 'react';
import { useState } from 'react';
import { useRef } from 'react';
import { Navigate } from 'react-router-dom';
import { Article, Content, Header, HeaderTitle, Title, WriteButton } from './style';

const BoardWrite = () => {
  const [list, setList] = useState();
  const titleRef = useRef();
  const contentRef = useRef();

  const onClicWrite = (event) => {
    event.preventDefault();

    const param = {
      title: titleRef.current.value,
      content: contentRef.current.innerText
    };

    axios.post('/api/board/write', param)
      .then((res) => {
        const board = res.data.body;
        console.log(res);
        alert(res.data.msg);
        setList(board);
      })
      .catch((err) => {
        console.error(err);
        alert(err.response.data.msg);
      });
  }

  if (list) {
    return (
      <Navigate to="/board/list" replace={true} />
    );
  }

  return <>
    <Article>
      <Header>
        <HeaderTitle>글쓰기</HeaderTitle>
        <WriteButton onClick={onClicWrite}>등록</WriteButton>
      </Header>
      <section>
        <Title ref={titleRef} placeholder='제목을 입력해주세요.' />
        <Content ref={contentRef} placeholder='내용을 입력하세요.' contentEditable></Content>
      </section>
    </Article>
  </>;
};

export default BoardWrite;