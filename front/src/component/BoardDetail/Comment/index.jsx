/* eslint-disable */
import React from 'react';
import { useParams } from 'react-router-dom';
import useSWR from 'swr';
import { boardDetailFetcher, deleteComment, disableEditing, editComment, enableEditing } from '../../../page/BoardDetail/action';
import LeftComment from './LeftComment';
import RightComment from './RightComment';

const Comment = ({ boardLoginId, comment }) => {
  const params = useParams();
  const { data: board, mutate } = useSWR(`/api/board/list/${params.boardId}`, boardDetailFetcher);

  const onClickDeleteComment = (commentId) => (event) => {
    event.preventDefault();
    deleteComment(mutate, commentId);
  };

  const onClickEnableEditing = (commentId) => () => enableEditing(mutate, commentId, board);

  const onClickDisableEditing = (comment) => () => disableEditing(mutate, comment, board);

  const onClickEditComment = (event, commentId, content) => {
    event.preventDefault();
    const commentParam = { comment: content };
    editComment(mutate, commentId, commentParam);
  };

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