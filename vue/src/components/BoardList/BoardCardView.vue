<template>
  <div class="list-box">
    <img :src="deleteImg" class="delete-button" @click="onClickDelete(board.boardId)">

    <div>
      <div class="title">
        <RouterLink :to="`/board/list/${board.boardId}`">{{ props.board.title }}</RouterLink>
      </div>
      <div class="content">{{ props.board.content }}</div>
    </div>

    <div class="info">
      <div>작성자: {{ props.board.loginId }}</div>
      <div>작성일: {{ props.board.createTimeStr }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import deleteImg from "../../assets/img/x.png";
const props = defineProps<{ board: Board; }>();
// const emit = defineEmits<{ (action: "deleteBoard", boardId: number): void }>();
const emit = defineEmits(["deleteBoard"]);

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

const onClickDelete = (boardId: number) => emit("deleteBoard", boardId);
  
</script>

<style scoped lang="scss">
.list-box {
  border: .5rem solid rgb(209, 191, 245);
  height: 10rem;
  margin: 1rem 0;
  border-radius: 1rem;
  padding: 1rem;
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  transition: .2s;
  box-shadow: 0 5px 10px rgb(0 0 0 / 20%);

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 10px rgb(0 0 0 / 40%);
  }
}

.delete-button {
  position: absolute;
  top: .5rem;
  right: .5rem;
  width: 1.5rem;
  cursor: pointer;
}

.title {
  margin: .5rem 0;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;

  & a {
    font-size: 2rem;
    text-decoration: none;
    color: rgb(103, 64, 182);
    display: block;
  }
}

.content {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  color: rgb(103, 64, 182);
}

.info {
  bottom: .5rem;
  left: .5rem;
  font-size: small;
  opacity: 0.4;
  margin: .5rem 0;
}
</style>