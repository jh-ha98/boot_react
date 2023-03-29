/* eslint-disable */
import axios from 'axios';
import React, { useCallback, useRef } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import useSWR from 'swr';
import Comment from '../../component/BoardDetail/Comment';
import { boardDetailFetcher, createComment } from './action';
import { Button, ButtonWrap, CommentBox, Content, CountComment, Hr, Info, InfoLoginId, InfoTime, InfoWrap, Textarea, TitleWrap, Wrap } from './style';
import { DefaultButton } from '../../style/buttons';

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

    createComment(mutate, param);
    commentRef.current.value = '';
  }, [board]);

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
    <Wrap>
      <TitleWrap>
        <h2>{board.title}</h2>
        <ButtonWrap>
          <Link to={`/board/update/${params.boardId}`}>
            <Button>수정</Button>
          </Link>
          <Button onClick={onClickDeleteBoard(params.boardId)}>삭제</Button>
        </ButtonWrap>
      </TitleWrap>
      <InfoWrap>
        <Info>
          <InfoLoginId>{board.loginId}</InfoLoginId>
          <InfoTime>{board.createTimeStr}</InfoTime>
        </Info>
        <CountComment>댓글 {board?.comments?.length}</CountComment>
      </InfoWrap>

      <hr />

      <Content>{board.content}</Content>

      <section>
        <Hr />
        <h3>댓글</h3>
        <CommentBox>
          {board?.comments?.map((comment, index) => <Comment key={index} boardLoginId={board.loginId} comment={comment} />)}
        </CommentBox>

        <div>
          <Textarea maxLength='100' ref={commentRef} placeholder='댓글을 입력하세요.' contentEditable suppressContentEditableWarning />
          <DefaultButton onClick={onClickCreateComment} >댓글달기</DefaultButton>
        </div>
      </section>
    </Wrap>
  )
};

export default BoardDetail;