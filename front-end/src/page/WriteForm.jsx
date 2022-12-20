import axios from "axios";
import React from "react";
import { useState } from "react";
import { useRef } from "react";
import { Navigate } from "react-router-dom";

const WriteForm = () => {
  const [list, setList] = useState();
  const titleRef = useRef();
  const contentRef = useRef();

  const onClicWrite = (event) => {
    event.preventDefault()

    const param = {
      title: titleRef.current.value,
      content: contentRef.current.value
    };

    axios.post('/api/board/write-form', param)
      .then((res) => {
        const board = res.data.body;
        console.log(res);
        alert(res.data.msg);
        setList(board);
      })
      .catch((error) => {
        console.error(error);
      })
  }

  // if (list) {
  //   return (
  //     <Navigate to="/board/board-list" replace={true} />
  //   )
  // }

  return (
    <div>
      <form>
        <label>제목</label>
        <input type='text' ref={titleRef} />
        <label>내용</label>
        <textarea ref={contentRef} />
        <button type="submit" onClick={onClicWrite}>작성</button>
      </form>
    </div>


  );
};
export default WriteForm;