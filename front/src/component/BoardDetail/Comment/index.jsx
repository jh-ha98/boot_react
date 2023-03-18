import React from 'react';
import LeftComment from './LeftComment';
import RightComment from './RightComment';

const Comment = ({ boardLoginId, comment, onClickEnableEditing, onClickDisableEditing, onClickEditComment, onClickDeleteComment }) => {
  return (<>
    {boardLoginId !== comment.loginId
      ? <LeftComment
        comment={comment}
        onClickEnableEditing={onClickEnableEditing}
        onClickDisableEditing={onClickDisableEditing}
        onClickEditComment={onClickEditComment}
        onClickDeleteComment={onClickDeleteComment}
      />
      : <RightComment
        comment={comment}
        onClickEnableEditing={onClickEnableEditing}
        onClickDisableEditing={onClickDisableEditing}
        onClickEditComment={onClickEditComment}
        onClickDeleteComment={onClickDeleteComment}
      />}
  </>)
};

export default Comment;