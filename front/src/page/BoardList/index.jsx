/* eslint-disable */
import axios from "axios";
import React, { useCallback } from "react";
import { useState } from "react";
import { Link } from 'react-router-dom';
import { DefaultButton } from "../../style/buttons";
import { PageButton, Serach, SerachInput, Wrap } from "./style";
import useSWR from "swr";
import Page from "../../component/BoardList/Page";

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

  const pages = []
  for (let i = 0; i <= page; i++) {
    pages.push(<Page key={i} page={i} search={search} onClickDelete={onClickDelete} />)
  }

  const onClickPage = () => {
    if (messageBox.isLast) return;
    setPage(page + 1);
  };

  return <>
    <Wrap>
      <Serach>
        <SerachInput type="text" value={search} placeholder="검색" onChange={onChangeSearch} />
      </Serach>
      <Link to={'/board/write'}>
        <DefaultButton>글 작성</DefaultButton>
      </Link>
      {pages}
      {/* <PageButton onClick={onClickPage} disabled={messageBox?.isLast ? true : false}>{messageBox?.isLast ? '더못봄' : '더보기'}</PageButton> */}
      <PageButton onClick={onClickPage} disabled={messageBox?.isLast ? true : false}>더보기</PageButton>
    </Wrap>
  </>;
}

export default BoardList;