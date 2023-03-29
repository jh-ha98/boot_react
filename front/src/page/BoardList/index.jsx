/* eslint-disable */
import axios from "axios";
import React, { useMemo } from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from 'react-router-dom';
import { DefaultButton } from "../../style/buttons";
import deleteImg from "../../resources/img/x.png";
import { Content, DeleteButton, Info, ListBox, Serach, SerachInput, Title, Wrap } from "./style";

const BoardList = () => {
  const [boardList, setBoardList] = useState([]);
  const [search, setSearch] = useState("");

  useEffect(() => {
    axios.get('/api/board/list')
      .then((res) => {
        const boardData = res.data;
        console.log(boardData);
        setBoardList(boardData);
      })
      .catch((error) => {
        console.error(error);
      })
  }, []);

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
    } else {
      return;
    }
  };

  const filteredList = useMemo(() =>
    boardList.filter((board) => board.title.toLowerCase().includes(search.toLowerCase())), [boardList, search]);

  return (
    <Wrap>
      <Serach>
        <SerachInput type="text" value={search} placeholder="검색" onChange={onChangeSearch} />
      </Serach>
      <Link to={'/board/write'}>
        <DefaultButton>글 작성</DefaultButton>
      </Link>

      {filteredList.map((board, index) =>
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
    </Wrap>
  );
}

export default BoardList;