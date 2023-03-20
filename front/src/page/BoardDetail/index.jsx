/* eslint-disable */
import axios from 'axios';
import React, { useCallback, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import useSWR from 'swr';
import style from './style.module.css';
import buttonStyle from '../../style/buttons.module.css';
import Comment from '../../component/BoardDetail/Comment';
import { writeComment } from './action';

const boardDetailFetcher = url => axios.get(url).then(res => res.data);

const BoardDetail = () => {
  const params = useParams();
  const navigate = useNavigate();
  const { data: board, isLoading, mutate } = useSWR(`/api/board/list/${params.boardId}`, boardDetailFetcher);
  const commentRef = useRef();

  const onClickCreateComment = useCallback(() => {
    const param = {
      boardId: params.boardId,
      comment: commentRef.current.value,
    };

    writeComment(mutate, param);
    commentRef.current.value = '';
  }, [board]);

  const onClickDeleteComment = (commentId) => (event) => {
    event.preventDefault();

    if (!confirm('댓글을 삭제하시겠습니까?')) return;

    axios.delete(`/api/comment/delete/${commentId}`)
      .then((res) => {
        alert(res.data.msg);
        mutate();
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data.msg);
      });
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

    mutate(newBoard, { revalidate: false });
  };

  const onClickDisableEditing = (comment) => () => {
    comment.editable = false;
    const newBoard = { ...board, comments: board.comments.map(comment => ({ ...comment })) };
    mutate(newBoard, { revalidate: false });
  };

  const onClickEditComment = (event, commentId, content) => {
    event.preventDefault();
    const commentParam = { comment: content };

    if (!confirm('댓글을 수정하시겠습니까?')) return;

    axios.put(`/api/comment/update/${commentId}`, commentParam)
      .then((res) => {
        mutate();
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
        alert(res.data.msg);
        navigate('/board/list');
      })
      .catch((error) => {
        console.error(error);
        alert(error.response.data.msg);
      });
  };

  if (isLoading) return <div>로딩중...</div>;

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