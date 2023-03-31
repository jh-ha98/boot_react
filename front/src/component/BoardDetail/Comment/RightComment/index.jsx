/* eslint-disable */
import React, { useRef } from 'react';
import deleteImg from '../../../../resources/img/x.png';
import updateImg from '../../../../resources/img/modify.png';
import { CommentBox, ImgButton, LoginId, RightWrap, TextComment, TimeStr, UpdateButton, UpdateComment } from './style';

const RightComment = ({ comment, onClickEnableEditing, onClickDisableEditing, onClickEditComment, onClickDeleteComment }) => {
  const contentRef = useRef();

  const _onClickEditComment = (commentId) => (event) => onClickEditComment(event, commentId, contentRef.current.innerText);

  return (<>
    <RightWrap>
      {comment.editable
        ? (<>
          <ImgButton src={deleteImg} onClick={onClickDisableEditing(comment)} />
          <UpdateButton onClick={_onClickEditComment(comment.commentId)}>확인</UpdateButton>
        </>)
        : (<>
          <ImgButton src={deleteImg} onClick={onClickDeleteComment(comment.commentId)} />
          <ImgButton src={updateImg} onClick={onClickEnableEditing(comment.commentId)} />
        </>)
      }
      <CommentBox>
        <LoginId>{comment.loginId}</LoginId>
        {comment.editable
          ? <UpdateComment ref={contentRef} contentEditable suppressContentEditableWarning>{comment.comment}</UpdateComment>
          : (<TextComment >{comment.comment}</TextComment>)
        }
        <TimeStr>{comment.createTimeStr}</TimeStr>
      </CommentBox>
    </RightWrap>
  </>);
};

export default RightComment;