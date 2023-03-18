/* eslint-disable */
import axios from "axios";
import React, { useCallback, useEffect, useRef } from "react";
import { useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import style from "./style.module.css";
import buttonStyle from "../../style/buttons.module.css";
import deleteImg from "../../resources/img/x.png";
import updateImg from "../../resources/img/modify.png";

const BoardDetail = () => {
  const params = useParams();
  const [boardDetailView, setBoardDetailView] = useState({});
  const [textarea, setTextarea] = useState('');
  const commentRef = useRef();
  const navigate = useNavigate();
  const contentRef = useRef();

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

  const onClickCreateComment = useCallback(() => {
    const param = {
      boardId: params.boardId,
      comment: commentRef.current.value,
    };

    axios.post('/api/comment/write', param)
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

    if (!confirm('댓글을 삭제하시겠습니까?')) return;
    axios.delete(`/api/comment/delete/${commentId}`)
      .then((res) => {
        console.log(res);
        const newBoardDetail = {
          ...boardDetailView,
          comments: boardDetailView.comments.filter(comments => comments.commentId !== commentId)
        };
        setBoardDetailView(newBoardDetail);
        alert(res.data.msg);
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data.msg);
      })
  };

  const onClickEdit = (commentId, commentText) => () => {
    const newComments = boardDetailView.comments.map(comment => {
      const editable = comment.commentId === commentId ? true : false;
      return { ...comment, editable };
    });

    const newBoardDetailView = {
      ...boardDetailView,
      comments: newComments
    };

    setBoardDetailView(newBoardDetailView);
    setTextarea(commentText);
  };

  const onClickUpdateBack2 = (comment) => () => {
    comment.editable = false;
    const newBoardDetailView = { ...boardDetailView };
    setBoardDetailView(newBoardDetailView);
  };

  const onClickUpdate = (commentId) => (event) => {
    event.preventDefault();

    console.log(commentRef.current.value)
    const commentParam = { comment: contentRef.current.innerText };

    if (!confirm('댓글을 수정하시겠습니까?')) return;
    axios.put(`/api/comment/update/${commentId}`, commentParam)
      .then((res) => {
        // const findCommment = boardDetailView.comments.find(comment => comment.commentId === commentId ? true : false);
        // findCommment.editable = false;
        // findCommment.comment = textarea;

        // const newBoardDetail = {
        //   ...boardDetailView,
        //   comments: [...comments]
        // }

        const updateCommentDetail = { ...boardDetailView };

        const newComments = updateCommentDetail.comments.map((comment) => {
          if (comment.commentId === commentId) {
            const copyComment = { ...comment };
            copyComment.editable = false;
            copyComment.comment = contentRef.current.innerText;
            return copyComment;
          } else {
            return comment;
          }
        });

        updateCommentDetail.comments = newComments;

        setBoardDetailView(updateCommentDetail);
        alert(res.data.msg);
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data.msg);
      });
  };

  const onClickDeleteBoard = (boardId) => (event) => {
    event.preventDefault();

    if (confirm('게시글을 삭제하시겠습니까?')) {
      axios.delete(`/api/board/delete/${boardId}`)
        .then((res) => {
          const deleteBoard = { ...boardDetailView };
          alert(res.data.msg);
          setBoardDetailView(deleteBoard);
          navigate('/board/list');
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
            {comment.editable
              ? <span className={style['text-comment-content']} ref={contentRef} contentEditable suppressContentEditableWarning>{textarea}</span>
              : (<pre className={style['text-comment']}>{comment.comment}</pre>)
            }
            <div className={style.createTimeStr}>{comment.createTimeStr}</div>
          </div>

          {comment.editable
            ? (<>
              {/* <img src={deleteImg} className={style['img-button']} onClick={onClickDelete(comment.commentId)} /> */}
              <img src={deleteImg}  onClick={onClickUpdateBack2(comment)} className={style['img-button']}/>
              <button className={style['update-button']} onClick={onClickUpdate(comment.commentId)}>확인</button>
            </>)
            : (<>
              <img src={deleteImg} className={style['img-button']} onClick={onClickDelete(comment.commentId)} />
              <img src={updateImg} onClick={onClickEdit(comment.commentId, comment.comment)} className={style['img-button']} />
            </>)
          }

        </div>
      );
    else
      result = (
        <div className={style['comment-right-wrap']} key={index}>
          {comment.editable
            ? (<>
              {/* <img src={deleteImg} className={style['img-button']} onClick={onClickDelete(comment.commentId)} /> */}
              <img src={deleteImg} onClick={onClickUpdateBack2(comment)} className={style['img-button']}/>
              <button className={style['update-button']} onClick={onClickUpdate(comment.commentId)}>확인</button>
            </>)
            : (<>
              <img src={deleteImg} className={style['img-button']} onClick={onClickDelete(comment.commentId)} />
              <img src={updateImg} onClick={onClickEdit(comment.commentId, comment.comment)} className={style['img-button']} />
            </>)
          }
          <div className={style['comment-right-box']}>
            <div style={{ textAlign: "end" }}>{comment.loginId}</div>
            {comment.editable
              ? <span className={style['text-comment-content']} ref={contentRef} contentEditable suppressContentEditableWarning>{textarea}</span>
              : (<pre className={style['text-comment']}>{comment.comment}</pre>)
            }
            <div className={style.createTimeStr}>{comment.createTimeStr}</div>
          </div>
        </div>
      );

    return result;
  }, [boardDetailView, textarea]);

  return (
    <div className={style.wrap}>
      <div className={style.titleWrap}>
        <h2>{boardDetailView.title}</h2>
        <div className={style.buttonWrap}>
          <Link to={`/board/update/${params.boardId}`}>
            <button className={`${buttonStyle["default-button"]} ${style.button}`}>수정</button>
          </Link>
          <button className={`${buttonStyle["default-button"]} ${style.button}`} onClick={onClickDeleteBoard(params.boardId)}>삭제</button>
        </div>
      </div>
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
        <hr className={style.hr} />
        <h3>댓글</h3>
        <div className={style['section-box']}>
          {boardDetailView?.comments?.map((comment, index) => makeCommentBox(comment, index))}
        </div>

        <div>
          <textarea className={style.textarea} maxLength="100" ref={commentRef} placeholder='댓글을 입력하세요' contentEditable suppressContentEditableWarning />
        </div>
        <button onClick={onClickCreateComment} className={buttonStyle['default-button']}>댓글달기</button>
      </section>
    </div>
  )
};

export default BoardDetail;

// const onClickUpdateBack = () => {
//   const originComments = boardDetailView.comments;
//   const newComments = [];
//   for (let i = 0; i < originComments.length; i++) {
//     const originComment = originComments[i];
//     const newComment = {
//       ...originComment,
//       editable: false
//     };

//     newComments.push(newComment);
//   }

//   const newBoardDetailView = {
//     ...boardDetailView,
//     comments: newComments
//   };

//   setBoardDetailView(newBoardDetailView);
// };
