/* eslint-disable */
import axios from "axios";
import React, { useCallback, useMemo } from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from 'react-router-dom';
import { DefaultButton } from "../../style/buttons";
import deleteImg from "../../resources/img/x.png";
import { Content, DeleteButton, Info, ListBox, PageButton, Serach, SerachInput, Title, Wrap } from "./style";
import useSWR from "swr";

const boardListFetcher = ([url, page]) => axios.get(url, { params: { page: page } }).then(res => res.data);

const BoardList = () => {
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const { data: messageBox, mutate } = useSWR(['/api/board/list', page], boardListFetcher);

  const onChangeSearch = (event) => {
    event.preventDefault();
    setSearch(event.target.value);
  }

  const onClickDelete = useCallback((boardId) => (event) => {
    event.preventDefault();

    if (!confirm('게시글을 삭제하시겠습니까?')) return;

    const newBoardList = messageBox?.body?.filter(boards => boards.boardId !== boardId)
    mutate(newBoardList, { revalidate: false });

    axios.delete(`/api/board/delete/${boardId}`)
      .then((res) => {
        alert(res.data.msg);
        mutate();
      })
      .catch((error) => {
        console.log(error);
        alert(error.response.data.msg);
      })
  }, [messageBox]);

  const onClickPage = () => {
    if (messageBox.isLast) return;
    setPage((prev) => page + 1);
  };

  const filteredList = useMemo(() =>
    messageBox?.body?.filter((board) => board.title.toLowerCase().includes(search.toLowerCase())), [messageBox, search]);

  return (
    <Wrap>
      <Serach>
        <SerachInput type="text" value={search} placeholder="검색" onChange={onChangeSearch} />
      </Serach>
      <Link to={'/board/write'}>
        <DefaultButton>글 작성</DefaultButton>
      </Link>

      {filteredList?.map((board, index) =>
        <ListBox key={index}>
          <DeleteButton src={deleteImg} onClick={onClickDelete(board.boardId)} />

          <div>
            <Title>
              <Link to={`/board/list/` + board.boardId}>{board.title}</Link>
            </Title>
            <Content>{board.content}</Content>
          </div>

          <Info>
            <div>작성자: {board.loginId}</div>
            <div>작성일: {board.createTimeStr}</div>
          </Info>
        </ListBox>
      )}
      {/* <PageButton onClick={onClickPage} disabled={isLast ? true : false}>{isLast ? '더못봄' : '더보기'}</PageButton> */}
      <PageButton onClick={onClickPage}>더보기</PageButton>
    </Wrap>
  );
}

export default BoardList;