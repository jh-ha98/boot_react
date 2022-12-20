import axios from "axios";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";
import { Link } from 'react-router-dom';
import style from "./style.module.css";

const BoardList = () => {
  const [boardList, setBoardList] = useState([]);

  useEffect(() => {
    axios.get('/api/board/board-list')
      .then((res) => {
        const boardData = res.data;
        console.log(boardData);
        setBoardList(boardData);
      })
      .catch((error) => {
        console.error(error);
      })
  }, []);

  return (
    <div>
      <Link to={'/board/write-form'}>
        <button >글 작성</button>
      </Link>
      {boardList.map((board, index) =>
        // <tr key={index}>
        //   <td> {index + 1}</td>
        //   <td> {board.title}</td>
        //   <td> {board.content}</td>
        //   <td> {board.memberId}</td>
        // </tr>
        <div key={index} className={style.wrap}>
          <div>
            <Link to={`/board/board-list/` + board.boardId}>{board.title}</Link>
          </div>
          <div>{board.content}</div>
        </div>
      )}
    </div>

  );
}

export default BoardList;