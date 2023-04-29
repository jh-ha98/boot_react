/* eslint-disable */
import axios from "axios";
import React, { useMemo } from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from 'react-router-dom';
import { DefaultButton } from "../../style/buttons";
import deleteImg from "../../resources/img/x.png";
import { Content, DeleteButton, Info, ListBox, PageButton, Serach, SerachInput, Title, Wrap } from "./style";
import useSWR from "swr";

const boardListFetcher = ([url, page]) => axios.get(url, { params: { page: page } }).then(res => res.data);

const BoardList = () => {
  const [boardList, setBoardList] = useState([]);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const { data: messageBox } = useSWR(['/api/board/list', page], boardListFetcher);

  useEffect(() => {
    if (messageBox !== undefined)
      setBoardList([...boardList, ...messageBox.body])
  }, [messageBox]);

  // const getBoardData = () => {
  //   const params = { page };
  //   axios.get('/api/board/list', { params })
  //     .then((res) => {
  //       const boardData = res.data.body;

  //       setBoardList([...boardList, ...boardData]);
  //       setPage(page + 1);
  //       setIsLast(res.data.isLast);
  //     })
  //     .catch((error) => {
  //       console.error(error);
  //     })
  // };

  // useEffect(() => {
  //   getBoardData();
  // }, []);

  const onChangeSearch = (event) => {
    event.preventDefault();
    setSearch(event.target.value);
  }

  const onClickDelete = (boardId) => (event) => {
    event.preventDefault();
    if (confirm('삭제하시겠습니까?')) {
      axios.delete(`/api/board/delete/${boardId}`)
        .then((res) => {
          const newBoardList = [...boardList];
          setBoardList(newBoardList.filter(boards => boards.boardId !== boardId));
          alert(res.data.msg);
        })
        .catch((error) => {
          console.log(error);
          alert(error.response.data.msg);
        })
    }
  };

  const onClickPage = () => {
    if (messageBox.isLast) return;
    // getBoardData();
    setPage(page + 1);
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