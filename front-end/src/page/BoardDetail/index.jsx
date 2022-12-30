/* eslint-disable */
import axios from "axios";
import React, { useEffect, useRef } from "react";
import { useState } from "react";
import { useParams } from "react-router-dom";
import style from "./style.module.css";

const BoardDetail = () => {
  const [boardDetailView, setBoardDetailView] = useState({});
  const params = useParams();

  useEffect(() => {
    const boardId = params.boardId;

    axios.get(`/api/board/list/${boardId}`)
      .then((res) => {
        const boardData = res.data;
        console.log(boardData);
        setBoardDetailView(boardData);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const commentRef = useRef();

  const onClickComment = () => {
    const param = {
      boardId: params.boardId,
      comment: commentRef.current.value,
    };

    axios.post('/api/comment/write', null, { params: param })
      .then((res) => {
        console.log(res);
        const comment = res.data.body;

        // 전개구문 사용하기
        const newAddData = {
          ...boardDetailView,
          comments: [
            ...boardDetailView.comments,
            comment
          ]
        };

        setBoardDetailView(newAddData);
        alert(res.data.msg);
      })
      .catch((err) => {
        console.error(err);
        alert(err.response.data.msg);
      });
  };

  return (
    <div>
      <h3>{boardDetailView.title}</h3>
      <div>
        {boardDetailView.memberId}
      </div>
      <div>
        {boardDetailView.createTimeStr}
      </div>
      <div className={style.content}>
        {boardDetailView.content}
      </div>

      <hr />

      <section>
        <div>
          {boardDetailView?.comments?.map((comment, index) =>
            <div key={index}>
              {comment.loginId}
              <br />
              {comment.comment}
            </div>
          )}
        </div>

        <button onClick={onClickComment}>댓글 작성</button>
        <div>
          <textarea ref={commentRef} placeholder='댓글을 입력하세요' />
        </div>
      </section>
    </div>
  )

}

export default BoardDetail;
