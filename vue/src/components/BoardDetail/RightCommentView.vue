<template>
  <div class="right-wrap">
    <div v-if="comment.editable">
      <img :src="deleteImg" class="img-button" />
      <button class="update-button">확인</button>
    </div>
    <div v-else>
      <img :src="deleteImg" class="img-button" />
      <img :src="updateImg" class="img-button" />
    </div>

    <div class="comment-box">
      <div class="login-id">{{ props.comment.loginId }}</div>
      <div v-if="comment.editable">
        <span class="update-comment" contentEditable suppressContentEditableWarning ref="content">{{
          props.comment.comment }}</span>
      </div>
      <div v-else>
        <pre class="text-comment">{{ props.comment.comment }}</pre>
      </div>
      <div class="time-str">{{ props.comment.createTimeStr }}</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import deleteImg from '../../assets/img/x.png';
import updateImg from "../../assets/img/modify.png";

const content = ref<HTMLSpanElement>();
const props = defineProps<{ comment: Comment; }>();

interface Comment {
  comment: string;
  commentId: number;
  createTime: string;
  createTimeStr: string;
  loginId: string;
  updateTime: string;
  updateTimeStr: string;
  editable: any;
}
</script>

<style scoped lang="scss">
.right-wrap {
  display: flex;
  justify-content: flex-end;
  margin: 1rem 0;
}

.comment-box {
  position: relative;
  font-size: 1rem;
  line-height: 1.5rem;
  min-width: 15rem;
  max-width: fit-content;
  background: #c4f0db;
  border-radius: 2rem;
  padding: 1rem;
  color: #000;
  margin: 0;
}

.update-comment {
  overflow-wrap: break-word;
  width: 100%;
  line-height: 1.5rem;
  min-height: 6rem;
  resize: none;
  white-space: pre-wrap;
  background-color: white;
  display: block;
  border-radius: 1rem;
  padding: .5rem;
  box-sizing: border-box;
}

.text-comment {
  overflow-wrap: break-word;
  width: 100%;
  line-height: 1.5rem;
  height: auto;
  min-height: 5rem;
  resize: none;
  white-space: pre-wrap;
  margin: 0;
}

.time-str {
  font-size: small;
  opacity: 0.5;
  position: relative;
}

.update-button {
  align-self: flex-end;
  margin: 0 .2rem;
}

.img-button {
  align-self: flex-end;
  width: 1rem;
  cursor: pointer;
}

.login-id {
  text-align: end;
}
</style>