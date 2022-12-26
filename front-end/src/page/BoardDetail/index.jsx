import axios from "axios";
import React, { useEffect } from "react";
import { useState } from "react";
import { useParams } from "react-router-dom";
import style from "./style.module.css";

const BoardDetail = () => {
  const [boardDetail, setBoardDetail] = useState({});
  const params = useParams();

  useEffect(() => {
    const boardId = params.boardId;

    axios.get(`/api/board/list/${boardId}`)
      .then((res) => {
        const boardData = res.data;
        console.log(boardData);
        setBoardDetail(boardData);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <div className={style.detailWrapper}>
      <div className={style.detailRow}>
        <label>제목</label>
        <label>{boardDetail.title}</label>
      </div>
      <div className={style.detailRow}>
        <label>내용</label>
        <label>{boardDetail.content}</label>
      </div>
      <div className={style.detailRow}>
        <label>작성일</label>
        <label>{boardDetail.createTime}</label>
      </div>
      <div className={style.detailRow}>
        <label>작성자</label>
        <label>{boardDetail.memberId}</label>
      </div>
    </div>
  )

}

export default BoardDetail;
