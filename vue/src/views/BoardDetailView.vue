<template>
  <div class="wrap">
    <div class="title-wrap">
      <h2>{{ board?.title }}</h2>
      <div class="button-wrap">
        <RouterLink :to="`/board/update/${$route.params.boardId}`">
          <button class="default-button button-group">수정</button>
        </RouterLink>
        <button class="default-button button-group" @click="onClickDeleteBoard(board?.boardId)">삭제</button>
      </div>
    </div>
    <div class="info-wrap">
      <div class="info">
        <div class="info-login-id">{{ board?.loginId }}</div>
        <div class="info-time">{{ board?.createTimeStr }}</div>
      </div>
      <div class="count-comment">댓글 {{ board?.comments?.length }}</div>
    </div>

    <hr />

    <div class="content">{{ board?.content }}</div>

    <section>
      <hr class="hr" />
      <h3>댓글</h3>
      <div class="comment-box">
        <!-- {{ board?.comments?.map((comment, index) =>
          <Comment key={ index } boardLoginId = { board.loginId } comment = { comment } />) }} -->
      </div>

      <div>
        <div ref="commentEl" class="textarea" placeholder="댓글을 입력하세요." suppressContentEditableWarning contenteditable>
        </div>
        <button class="default-button" @click="onClickCreateComment">댓글달기</button>
      </div>
    </section>
  </div>
</template>

<script setup lang="ts">
import router from '@/router';
import axios from 'axios';
import { ref, watchEffect } from 'vue';
import { useRoute } from 'vue-router';
import CommentView from '../components/BoardDetail/BoardCommentView.vue';

interface MessageBox<T> {
  vaild: boolean;
  isLast: boolean;
  msg: string;
  body: T;
};

type Board = {
  boardId: number;
  title: string;
  content: string;
  loginId: string;
  email: string;
  createTimeStr: string;
  createTime: string;
  comments: object[];
} | null;

const board = ref<Board>(null);
const route = useRoute();
const boardId = ref(route.params.boardId);
const commentEl = ref<HTMLDivElement>();

watchEffect(async () => {

  // const findBoard = await axios.get(`/api/board/list/${boardId.value}`).then(res => res.data);
  // const findComment = await axios.get(`/api/comment/list/${boardId.value}`).then(res => res.data);

  // findBoard.comments = findComment;
  // board.value = findBoard;

  const [findBoard, comments] = await Promise.all([
    axios.get(`/api/board/list/${boardId.value}`).then(res => res.data),
    axios.get(`/api/comment/list/${boardId.value}`).then(res => res.data)
  ]);

  findBoard.comments = comments;
  board.value = findBoard;
})

const onClickDeleteBoard = (boardId: number | undefined) => {
  if (!boardId) return;
  if (!confirm('게시글을 삭제하시겠습니까?')) return;

  axios.delete(`/api/board/delete/${boardId}`)
    .then((res) => {
      alert(res.data.msg);
      router.push({ path: '/board/list', replace: true });
    })
    .catch((error) => {
      console.error(error);
      alert(error.response.data.msg);
    });
}

const onClickCreateComment = (e: Event) => {
  e.preventDefault();

  const boardValue = board.value;
  const commentElValue = commentEl.value;
  if (!boardValue) return;
  if (!(commentElValue instanceof HTMLDivElement)) return;

  console.log(boardValue)

  const param = {
    boardId: boardId.value,
    comment: commentElValue.innerText
  }

  axios.post('/api/comment/write', param)
    .then(res => {
      const comment = res.data.body;
      alert(res.data.msg);
      // 전개구문 사용하기
      const newBoard = {
        ...boardValue,
        comments: [...boardValue.comments, comment]
      };
      board.value = newBoard;
      commentElValue.innerText = ''

    })
    .catch((err) => {
      console.error(err);
      alert(err.response.data.msg);
    });
}

</script>

<style scoped lang="scss">
.wrap {
  width: 100%;
  max-width: 80rem;
  margin: auto;
}

.title-wrap {
  display: flex;
  justify-content: space-between;
}

.button-wrap {
  display: flex;
  justify-content: end;
  align-items: center;
  padding: 0 1rem;
  width: 100%;
  margin-top: 0;
  margin-right: -1.5rem;
}

.button-group {
  max-width: 5rem;
  min-width: 5rem;
  font-size: 1.1rem;
  margin: 0 0.3rem;
}

.info-wrap {
  display: flex;
  justify-content: space-between;
}

.info {
  display: block;
}

.info-login-id {
  font-size: small;
  display: block;
}

.info-time {
  font-size: small;
  opacity: 0.5;
  display: block;
}

.count-comment {
  font-size: small;
  display: block;
}

.content {
  display: block;
  margin-top: 1rem;
  min-height: 10rem;
}

.hr {
  border: 0;
  border-top: 1px dashed #8c8c8c;
  border-bottom: 1px dashed #fff;
}

.comment-box {
  flex-direction: column;
}

.textarea {
  width: 100%;
  height: auto;
  min-height: 7rem;
  line-height: 1.75rem;
  resize: none;
  font-family: 'Noto Sans KR', sans-serif;
  font-size: 1rem;
  box-sizing: border-box;
  white-space: pre-wrap;
  background-color: rgb(209, 191, 245);
  border-radius: .5rem;
  padding: .5rem;
  border: none;
  outline: none;
  margin: 0 0 0.5rem 0;

  &:focus {
    box-shadow: 0 0 0 .1rem rgb(103, 64, 182);
  }

  &:empty:before {
    display: block;
    content: attr(placeholder);
    color: rgb(103, 64, 182);
  }
}
</style>