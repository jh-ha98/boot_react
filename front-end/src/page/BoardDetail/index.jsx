/* eslint-disable */
import axios from "axios";
import React, { useCallback, useEffect, useRef } from "react";
import { useState } from "react";
import { useParams } from "react-router-dom";
import style from "./style.module.css";
import buttonStyle from "../../style/buttons.module.css";

const BoardDetail = () => {
  const [boardDetailView, setBoardDetailView] = useState({});
  const params = useParams();

  useEffect(() => {
    const boardId = params.boardId;

    axios.get(`/api/board/list/${boardId}`)
      .then((res) => {
        const boardData = res.data;
        setBoardDetailView(boardData);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const commentRef = useRef();

  const onClickComment = useCallback(() => {
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
  }, [boardDetailView]);

  const onClickDelete = (commentId) => (event) => {
    event.preventDefault();
    if (window.confirm('삭제하시겠습니까?')) {
      axios.delete(`/api/comment/delete/${commentId}`)
        .then((res) => {
          alert(res.data.msg);
          const newBoardDetail = [{...boardDetailView}];
          setBoardDetailView(newBoardDetail.filter(comments => comments.commentId !== commentId));
        })
        .catch((error) => {
          console.log(error);
          alert(error.response.data.msg);
        })
    } else {
      return;
    }
  };

  const makeCommentBox = useCallback((comment, index) => {
    let result;

    if (boardDetailView.loginId !== comment.loginId)
      result = (
        <div className={style['comment-wrap']} key={index}>
          <div className={style['comment-left-box']}>
            <div>{comment.loginId}</div>
            <pre style={{ overflowWrap: 'break-word' }}>{comment.comment}</pre>
          </div>
          <button className={style['delete-button']} onClick={onClickDelete(comment.commentId)}>X</button>
        </div>
      );
    else
      result = (
        <div className={style['comment-right-wrap']} key={index}>
          <button className={style['delete-button']} onClick={onClickDelete(comment.commentId)}>X</button>
          <div className={style['comment-right-box']}>
            <div style={{ textAlign: "end" }}>{comment.loginId}</div>
            <pre style={{ overflowWrap: 'break-word' }}>{comment.comment}</pre>
          </div>
        </div>
      );

    return result;
  }, [boardDetailView]);

  return (
    <div className={style.wrap}>
      <h2>{boardDetailView.title}</h2>
      <div className={style.innerWrap}>
        <div className={style.info}>
          <div className={style.infoId}>
            {boardDetailView.loginId}
          </div>
          <div className={style.infoTime}>
            {boardDetailView.createTimeStr}
          </div>
        </div>
        <div className={style.countComment}>
          댓글 {boardDetailView?.comments?.length}
        </div>
      </div>

      <hr />

      <div className={style.content}>
        {boardDetailView.content}
      </div>

      <section>
        <div className={style['section-box']}>
          {boardDetailView?.comments?.map((comment, index) => makeCommentBox(comment, index))}
        </div>

        <div>
          <textarea className={style.textarea} maxLength="100" ref={commentRef} placeholder='댓글을 입력하세요' />
        </div>
        <button onClick={onClickComment} className={buttonStyle['default-button']}>댓글달기</button>
      </section >
    </div >
  )

};

export default BoardDetail;
