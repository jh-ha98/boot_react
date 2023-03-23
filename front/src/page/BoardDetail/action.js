/* eslint-disable */
import axios from 'axios';

export const boardDetailFetcher = url => axios.get(url).then(res => res.data);

export const createComment = (mutate, param) => {
  axios.post('/api/comment/write', param)
    .then(res => {
      alert(res.data.msg);
      mutate();
    })
    .catch((err) => {
      console.error(err);
      alert(err.response.data.msg);
    });
};

export const deleteComment = (mutate, commentId) => {
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

export const editComment = (mutate, commentId, commentParam) => {
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

export const enableEditing = (mutate, commentId, board) => {
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

export const disableEditing = (mutate, comment, board) => {
  comment.editable = false;
  const newBoard = { ...board, comments: board.comments.map(comment => ({ ...comment })) };
  mutate(newBoard, { revalidate: false });
}