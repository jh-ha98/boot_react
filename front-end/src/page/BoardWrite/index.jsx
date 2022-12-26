import axios from "axios";
import React from "react";
import { useState } from "react";
import { useRef } from "react";
import { Navigate } from "react-router-dom";
import buttonStyle from "../../style/buttons.module.css";
import style from "./style.module.css";

const WriteForm = () => {
  const [list, setList] = useState();
  const titleRef = useRef();
  const contentRef = useRef();

  const onClicWrite = (event) => {
    event.preventDefault()

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
      .catch((error) => {
        console.error(error);
      });
  }

  if (list) {
    return (
      <Navigate to="/board/list" replace={true} />
    );
  }

  return <>
    <article>
      <header className={style.header}>
        <h2>글쓰기</h2>
        <button className={`${buttonStyle["default-button"]} ${style.button}`} onClick={onClicWrite}>등록</button>
      </header>
      <section>
        <input ref={titleRef} className={style.title} placeholder='제목을 입력해주세요.' />
        <span ref={contentRef} className={style.content} placeholder='내용을 입력하세요.' contentEditable></span>
      </section>
    </article>
  </>;
};

export default WriteForm;