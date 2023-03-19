export const initialState = {
  boardId: null,
  title: null,
  content: null,
  loginId: null,
  email: null,
  createTimeStr: null,
  createTime: null,
  comments: []
};

export const GET_BOARD_LIST = 'GET_BOARD_LIST';
export const CREATE_COMMENT = 'CREATE_COMMENT';
export const DELETE_COMMENT = 'DELETE_COMMENT';
export const ENABLE_EDITING = 'ENABLE_EDITING';
export const DISENABLE_EDITING = 'DISENABLE_EDITING';
export const EDIT_COMMENT = 'EDIT_COMMENT';

export const reducer = (board, action) => {
  switch (action.type) {
    case GET_BOARD_LIST:
      return action.payload;
    case CREATE_COMMENT: {
      const comment = action.payload.body;

      // 전개구문 사용하기
      const newBoard = {
        ...board,
        comments: [
          ...board.comments,
          comment
        ]
      };

      return newBoard;
    }
    case DELETE_COMMENT: {
      const commentId = action.payload;

      const newBoard = {
        ...board,
        comments: board.comments.filter(comments => comments.commentId !== commentId)
      };

      return newBoard;
    }
    case ENABLE_EDITING: {
      const commentId = action.payload;
      const newComments = board.comments.map(comment => {
        const editable = comment.commentId === commentId ? true : false;
        return { ...comment, editable };
      });

      const newBoard = {
        ...board,
        comments: newComments
      };

      return newBoard;
    }
    case DISENABLE_EDITING: {
      const comment = action.payload;
      comment.editable = false;
      const newBoard = { ...board };
      return newBoard;
    }
    case EDIT_COMMENT: {
      const commentId = action.payload.commentId;
      const content = action.payload.content;
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
      return copyBoard;
    }
    default:
      throw new Error();
  }
};