import axios from "axios";
import React, { useEffect } from "react";
import { useState } from "react";
import { useParams } from "react-router-dom";

const BoardDetail = () => {
  const [boardDetail, setBoardDetail] = useState();
  const params = useParams();
  console.log(params);

  useEffect(() => {
    const boardId = params.boardId;
    axios.get(`/api/board/board-list/${boardId}`)
      .then((res) => {
        const boardData = res.data;
        console.log(boardData);
        setBoardDetail(boardData);
      })
      .catch((error) => {
        console.error(error);
      })
  });

  return (
    <div>
      {params.boardId}
    </div>

  )

}
export default BoardDetail;