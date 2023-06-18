<template>
  <div class="wrap">
    <div class="search">
      <input type="text" class="search-input" placeholder="검색" v-model="search" />
    </div>
    <RouterLink to="/board/write">
      <button class="default-button">글 작성</button>
    </RouterLink>
    <BoardCardView v-for="(board, index) in boards" :key="index" :board="board" @delete-board="deleteBoard" />
    <button class="default-button page-button" @click="searchBoard" :disabled="isLast ? true : false">더보기</button>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue';
import BoardCardView from '@/components/BoardList/BoardCardView.vue';

interface MessageBox<T> {
  vaild: boolean;
  isLast: boolean;
  msg: string;
  body: T;
};

interface Board {
  boardId: number;
  title: string;
  content: string;
  loginId: string;
  email: string;
  createTimeStr: string;
  createTime: string;
  comments: object[]
};

const search = ref<string>('');
const page = ref(0);
const boards = ref<Board[]>([]);
const isLast = ref<boolean>(false);

const searchBoard = async () => {
  if (isLast.value) return;

  const messageBox: MessageBox<Board[]> = await axios.get('/api/board/list', { params: { page: page.value } }).then(res => res.data);
  boards.value = [...boards.value, ...messageBox.body];
  isLast.value = messageBox.isLast;
  page.value++;
};

const deleteBoard = (boardId: number) => {
  if (!confirm('게시글을 삭제하시겠습니까?')) return;

  axios.delete(`/api/board/delete/${boardId}`)
    .then((res) => {
      alert(res.data.msg);
      boards.value = boards.value.filter(board => board.boardId !== boardId)
    })
    .catch((error) => {
      console.log(error);
      alert(error.response.data.msg);
    });
}
</script>

<style scoped lang="scss">
.wrap {
  width: 100%;
  max-width: 80rem;
  margin: auto;
}

.search {
  width: fit-content;
  margin: 0 auto;
  margin-right: 0;
  padding: 1rem;
}

.search-input {
  font-size: medium;
  padding: .5rem;
  width: 100%;
  height: 1.5rem;
  background-color: rgb(209, 191, 245);
  border: none;
  border-radius: .5rem;

  &:focus {
    outline: none;
    box-shadow: 0 0 0 .2rem rgb(103, 64, 182);
    transition: all .2s;
  }

  &::placeholder {
    color: rgb(103, 64, 182);
  }
}

.page-button {
  width: 100%;

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
}
</style>