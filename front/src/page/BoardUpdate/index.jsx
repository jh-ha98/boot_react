/* eslint-disable */
import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { Navigate, useParams } from "react-router-dom";
import { Article, Content, Header, HeaderTitle, Title, UpdateButton } from "./style";

const BoardUpdate = () => {
  const [form, setForm] = useState();
  const params = useParams();
  const titleRef = useRef();
  const contentRef = useRef();

  useEffect(() => {
    axios.get(`/api/board/select/${params.boardId}`)
      .catch((error) => error.response)
      .then((res) => {
        console.log(res.data);
        const board = res.data;
        titleRef.current.value = board.title;
        contentRef.current.innerText = board.content;
      })
      .catch((error) => console.log(error));
  });

  const onClickUpdate = (event) => {
    event.preventDefault();

    const param = {
      id: params.boardId,
      title: titleRef.current.value,
      content: contentRef.current.innerText
    };

    if (!confirm('게시글을 수정하시겠습니까?')) return;
    axios.put('/api/board/update', param)
      .then((res) => {
        const updateBoard = res.data;
        alert(res.data.msg);
        setForm(updateBoard);
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data.msg);
      });
  };

  if (form) {
    return (
      <Navigate to="/board/list" replace={true} />
    );
  };

  return <>
    <Article>
      <Header>
        <HeaderTitle>글 수정하기</HeaderTitle>
        <UpdateButton onClick={onClickUpdate}>수정</UpdateButton>
      </Header>
      <section>
        <Title ref={titleRef} placeholder='제목을 입력해주세요.' />
        <Content ref={contentRef} placeholder='내용을 입력하세요.' contentEditable></Content>
      </section>
    </Article>
  </>;
};

export default BoardUpdate;