import axios from "axios";
import React, { useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import buttonStyle from "../../style/buttons.module.css";
import style from "./style.module.css";

const BoardUpdate = () => {
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

  return <>
    <article className={style.article}>
      <header className={style.header}>
        <h2 className={style["header-title"]}>글 수정하기</h2>
        <button className={`${buttonStyle["default-button"]} ${style.button}`}>수정</button>
      </header>
      <section>
        <input ref={titleRef} className={style.title} placeholder='제목을 입력해주세요.' />
        <span ref={contentRef} className={style.content} placeholder='내용을 입력하세요.' contentEditable></span>
      </section>
    </article>
  </>;
};

export default BoardUpdate;