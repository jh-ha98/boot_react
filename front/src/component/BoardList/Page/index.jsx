import axios from "axios";
import deleteImg from "../../../resources/img/x.png";
import { Content, DeleteButton, Info, ListBox, Title } from "./style";
import useSWR from "swr";
import { useMemo } from "react";
import { Link } from "react-router-dom";

const boardListFetcher = ([url, page]) => axios.get(url, { params: { page: page } }).then(res => res.data);

const Page = ({ page, search, onClickDelete }) => {
  const { data: messageBox } = useSWR(['/api/board/list', page], boardListFetcher);

  const filteredList = useMemo(() =>
    messageBox?.body?.filter((board) => board.title.toLowerCase().includes(search.toLowerCase())), [messageBox, search]);

  return <>
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
  </>;
}

export default Page;