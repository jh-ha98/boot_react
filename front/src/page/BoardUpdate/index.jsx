/* eslint-disable */
import axios from "axios";
import React, { useEffect, useRef, useState } from "react";
import { Navigate, useParams } from "react-router-dom";
import buttonStyle from "../../style/buttons.module.css";
import style from "./style.module.css";

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
    <article className={style.article}>
      <header className={style.header}>
        <h2 className={style["header-title"]}>글 수정하기</h2>
        <button className={`${buttonStyle["default-button"]} ${style.button}`} onClick={onClickUpdate}>수정</button>
      </header>
      <section>
        <input ref={titleRef} className={style.title} placeholder='제목을 입력해주세요.' />
        <span ref={contentRef} className={style.content} placeholder='내용을 입력하세요.' contentEditable></span>
      </section>
    </article>
  </>;
};

export default BoardUpdate;