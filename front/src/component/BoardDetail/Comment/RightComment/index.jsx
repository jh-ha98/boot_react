import React, { useRef } from 'react';
import style from './style.module.css';
import deleteImg from '../../../../resources/img/x.png';
import updateImg from '../../../../resources/img/modify.png';

const RightComment = ({ comment, onClickEnableEditing, onClickDisableEditing, onClickEditComment, onClickDeleteComment }) => {
  const contentRef = useRef();

  const _onClickEditComment = (commentId) => (event) => onClickEditComment(event, commentId, contentRef.current.innerText);

  return (<>
    <div className={style['comment-right-wrap']}>
      {comment.editable
        ? (<>
          <img src={deleteImg} onClick={onClickDisableEditing(comment)} className={style['img-button']} />
          <button className={style['update-button']} onClick={_onClickEditComment(comment.commentId)}>확인</button>
        </>)
        : (<>
          <img src={deleteImg} className={style['img-button']} onClick={onClickDeleteComment(comment.commentId)} />
          <img src={updateImg} onClick={onClickEnableEditing(comment.commentId)} className={style['img-button']} />
        </>)
      }
      <div className={style['comment-right-box']}>
        <div style={{ textAlign: 'end' }}>{comment.loginId}</div>
        {comment.editable
          ? <span className={style['text-comment-content']} ref={contentRef} contentEditable suppressContentEditableWarning>{comment.comment}</span>
          : (<pre className={style['text-comment']}>{comment.comment}</pre>)
        }
        <div className={style.createTimeStr}>{comment.createTimeStr}</div>
      </div>
    </div>
  </>);
};

export default RightComment;

