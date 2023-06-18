<template>
  <article>
    <header>
      <h2 class="header-title">글쓰기</h2>
      <button class="default-button write-button" @click="onClickWrite">등록</button>
    </header>
    <section>
      <input class="title" placeholder="제목을 입력해주세요." v-model="title" />
      <span ref="content" class="content" placeholder="내용을 입력하세요." contenteditable></span>
    </section>
  </article>
</template>

<script setup lang="ts">
import router from '@/router';
import axios from 'axios';
import { ref } from 'vue';

type Boards = {
  title: string;
  content: string;
} | null;

const boards = ref<Boards>(null);
const title = ref<string>('');
const content = ref<HTMLSpanElement>();

const onClickWrite = (e: Event) => {
  e.preventDefault();

  const params = {
    title: title.value,
    content: content.value?.innerText,
  };

  axios.post('/api/board/write', params)
    .then((res) => {
      const board = res.data.body;
      console.log(res);
      alert(res.data.msg);
      boards.value = board;

      if (board) {
        router.push({ path: '/board/list', replace: true });
      }
    })
    .catch((err) => {
      console.error(err);
      alert(err.response.data.msg);
    });
}
</script>

<style scoped lang="scss">
article {
  width: 100%;
  max-width: 80rem;
  margin: auto;
}

header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  top: -1rem;
  background-color: white;
  padding: 0 1rem;
  width: 100%;
  margin: 0 -1rem;
}

.header-title {
  color: rgb(103, 64, 182);
}

.write-button {
  max-width: 5rem;
  min-width: 5rem;
  font-size: 1.1rem;
}

.title {
  display: block;
  width: 100%;
  box-sizing: border-box;
  height: 3rem;
  margin: 1rem 0;
  background-color: rgb(209, 191, 245);
  border: none;
  border-radius: .5rem;
  padding: .5rem;
  font-size: 1rem;
  outline: none;

  &::placeholder {
    color: rgb(103, 64, 182);
  }

  &:focus {
    box-shadow: 0 0 0 .1rem rgb(103, 64, 182);
  }
}

.content {
  display: block;
  width: 100%;
  box-sizing: border-box;
  height: 3rem;
  margin: 1rem 0;
  background-color: rgb(209, 191, 245);
  border: none;
  border-radius: .5rem;
  padding: .5rem;
  font-size: 1rem;
  outline: none;
  height: auto;
  min-height: 30rem;
  white-space: pre-wrap;
  overflow-wrap: break-word;
  word-break: break-all;

  &:focus {
    box-shadow: 0 0 0 .1rem rgb(103, 64, 182);
  }

  &:empty::before {
    display: block;
    content: attr(placeholder);
    color: rgb(103, 64, 182);
  }
}
</style>