/* eslint-disable */
import axios from "axios";
import React, { useMemo } from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from 'react-router-dom';
import buttonStyle from "../../style/buttons.module.css";
import deleteImg from "../../resources/img/x.png";
import style from "./style.module.css";

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
    <div className={style.wrap}>
      <div className={style.search}>
        <input className={style['search-input']} type="text" value={search} placeholder="검색" onChange={onChangeSearch} />
      </div>
      <Link to={'/board/write'}>
        <button className={buttonStyle['default-button']}>글 작성</button>
      </Link>

      {filteredList.map((board, index) =>
        <div key={index} className={style['box-wrap']}>
          <img src={deleteImg} className={style['delete-button']} onClick={onClickDelete(board.boardId)} />

          <div>
            <div className={style.title}>
              <Link to={`/board/list/` + board.boardId}>{board.title}</Link>
            </div>
            <div className={style.content}>{board.content}</div>
          </div>

          <div className={style.info}>
            <div>작성자: {board.loginId}</div>
            <div>작성일: {board.createTimeStr}</div>
          </div>
        </div>
      )}
    </div>

  );
}

export default BoardList;