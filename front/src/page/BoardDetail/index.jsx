/* eslint-disable */
import axios from 'axios';
import React, { useState, useCallback, useEffect, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import style from './style.module.css';
import buttonStyle from '../../style/buttons.module.css';
import Comment from '../../component/BoardDetail/Comment';

const BoardDetail = () => {
  const params = useParams();
  const navigate = useNavigate();
  const [board, setBoard] = useState({});
  const commentRef = useRef();

  useEffect(() => {
    const boardId = params.boardId;

    axios.get(`/api/board/list/${boardId}`)
      .then((res) => {
        const boardData = res.data;
        setBoard(boardData);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const onClickCreateComment = useCallback(() => {
    const param = {
      boardId: params.boardId,
      comment: commentRef.current.value,
    };

    axios.post('/api/comment/write', param)
      .then((res) => {
        const comment = res.data.body;

        // 전개구문 사용하기
        const newBoard = {
          ...board,
          comments: [
            ...board.comments,
            comment
          ]
        };
        setBoard(newBoard);
        commentRef.current.value = '';
        alert(res.data.msg);
      })
      .catch((err) => {
        console.error(err);
        alert(err.response.data.msg);
      });
  }, [board]);

  const onClickDeleteComment = (commentId) => (event) => {
    event.preventDefault();

    if (!confirm('댓글을 삭제하시겠습니까?')) return;
    axios.delete(`/api/comment/delete/${commentId}`)
      .then((res) => {
        console.log(res);
        const newBoardDetail = {
          ...board,
          comments: board.comments.filter(comments => comments.commentId !== commentId)
        };
        setBoard(newBoardDetail);
        alert(res.data.msg);
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data.msg);
      })
  };

  const onClickEnableEditing = (commentId) => () => {
    const newComments = board.comments.map(comment => {
      const editable = comment.commentId === commentId ? true : false;
      return { ...comment, editable };
    });

    const newBoard = {
      ...board,
      comments: newComments
    };

    setBoard(newBoard);
  };

  const onClickDisableEditing = (comment) => () => {
    comment.editable = false;
    const newBoard = { ...board };
    setBoard(newBoard);
  };

  const onClickEditComment = (event, commentId, content) => {
    event.preventDefault();
    const commentParam = { comment: content };

    if (!confirm('댓글을 수정하시겠습니까?')) return;

    axios.put(`/api/comment/update/${commentId}`, commentParam)
      .then((res) => {
        const copyBoard = { ...board };

        const newComments = copyBoard.comments.map((comment) => {
          if (comment.commentId === commentId) {
            const copyComment = { ...comment };
            copyComment.editable = false;
            copyComment.comment = content;
            return copyComment;
          } else {
            return comment;
          }
        });

        copyBoard.comments = newComments;
        setBoard(copyBoard);
        alert(res.data.msg);
      })
      .catch((error) => {
        console.error(error);
        alert(error.response.data.msg);
      });
  };

  const onClickDeleteBoard = (boardId) => (event) => {
    event.preventDefault();

    if (!confirm('게시글을 삭제하시겠습니까?')) return;

    axios.delete(`/api/board/delete/${boardId}`)
      .then((res) => {
        const deleteBoard = { ...board };
        alert(res.data.msg);
        setBoard(deleteBoard);
        navigate('/board/list');
      })
      .catch((error) => {
        console.error(error);
        alert(error.response.data.msg);
      });
  };

  return (
    <div className={style.wrap}>
      <div className={style.titleWrap}>
        <h2>{board.title}</h2>
        <div className={style.buttonWrap}>
          <Link to={`/board/update/${params.boardId}`}>
            <button className={`${buttonStyle['default-button']} ${style.button}`}>수정</button>
          </Link>
          <button className={`${buttonStyle['default-button']} ${style.button}`} onClick={onClickDeleteBoard(params.boardId)}>삭제</button>
        </div>
      </div>
      <div className={style.innerWrap}>
        <div className={style.info}>
          <div className={style.infoId}>
            {board.loginId}
          </div>
          <div className={style.infoTime}>
            {board.createTimeStr}
          </div>
        </div>
        <div className={style.countComment}>
          댓글 {board?.comments?.length}
        </div>
      </div>

      <hr />

      <div className={style.content}>
        {board.content}
      </div>

      <section>
        <hr className={style.hr} />
        <h3>댓글</h3>
        <div className={style['section-box']}>
          {board?.comments?.map((comment, index) => <Comment
            key={index}
            boardLoginId={board.loginId}
            comment={comment}
            onClickEnableEditing={onClickEnableEditing}
            onClickDisableEditing={onClickDisableEditing}
            onClickEditComment={onClickEditComment}
            onClickDeleteComment={onClickDeleteComment}
          />)}
        </div>

        <div>
          <textarea className={style.textarea} maxLength='100' ref={commentRef} placeholder='댓글을 입력하세요' contentEditable suppressContentEditableWarning />
          <button onClick={onClickCreateComment} className={buttonStyle['default-button']}>댓글달기</button>
        </div>
      </section>
    </div>
  )
};

export default BoardDetail;