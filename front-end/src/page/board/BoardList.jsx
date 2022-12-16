import axios from "axios";
import React from "react";
import { useEffect } from "react";
import { useState } from "react";

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
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>제목</th>
            <th>내용</th>
            <th>작성자</th>
          </tr>
        </thead>
        <tbody>
          {boardList.map((board, index) => {
            return (
              <tr key={index}>
                <td> {index + 1}</td>
                <td> {board.title}</td>
                <td> {board.content}</td>
                <td> {board.memberId}</td>
              </tr>
            )

          })}

        </tbody>
      </table>
    </div>

  )
}


export default BoardList;