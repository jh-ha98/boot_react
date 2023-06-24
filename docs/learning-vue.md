# Front-End 학습 정리
Vue를 이용한 사용자 인테페이스를 만들면서 정리한 학습내용입니다.
- [메인](../README.md)

## 목차
- [Vue](#vue)
  - [사용된 패키지](#사용된-패키지)
  - [명령어](#명령어)
  - [스타일](#스타일)
  - [전개구문 사용하기](#전개구문-사용하기)
  - [라우터 만들기](#라우터-만들기)
  - [SWR 사용하기](#swr-사용하기)

## Vue
해당 프로젝트에서는 VUE-CLI 를 사용하였습니다.

### 사용된 패키지
- `axios: ^1.4.0`
  - 서버측에 http 비동기 요청을 보내기위한 패키지.
- `vue: ^3.3.2`
  - vue를 사용하기위한 패키지
- `vue-router: ^4.2.0`
  - 페이지 이동을 위한 라우팅 패키지
- `pinia: ^2.0.36`
  - Vue.js용 스토어 라이브러리 및 상태 관리 프레임워크

### 명령어
```bash
# 개발환경 프론트 서버 시작 명령어
npm run dev
# 작성된 vue 코드를 build한 파일 (html, css, js, img...)
npm run build
```

### $의 의미 - 전역 객체 속성
private하게 사용하는게 아닌 public하게 사용하는 속성

### Props
- 상위 컴포넌트에서 하위 컴포넌트로 뿌려주는것
- 하위 컴포넌트에서 선언하고 상위에서 선언해둔 이름으로 보낼 수 있음.
```javascript
// 예제 코드
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
const props = defineProps<{ board: Board }>();

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
</script>
```
### Emit
- 하위 컴포넌트에서 상위 컴포넌트로 올려주는것
- 하위 컴포넌트에서 선언하고 상위에서 선언해둔 이름으로 받을 수 있음
```javascript
//예제 코드

// BoardCardView.vue 하위컴포넌트
<template>
  const emit = defineEmits(["deleteBoard"]);
</template>

<script setup lang="ts">
  const onClickDelete = (boardId: number) => emit("deleteBoard", boardId);
</script>
```
```javascript
// BoardListView.vue
<template>
  <BoardCardView v-for="(board, index) in boards" :key="index" :board="board" @delete-board="deleteBoard" />
</template>

<script setup lang="ts">
  import BoardCardView from '@/components/BoardList/BoardCardView.vue';

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
```

### ref() 
- String, Number, Object 등 어떤 타입에서든 사용 가능
- 접근방식:  .value를 붙여야 접근할 수 있고 `<template>` 안에서 사용할 때는 붙이지 않아도 됨(value 속성을 가진 객체이기 때문)
- 각각의 단일 속성을 선언

기본 사용방법
```typescript
<script setup lang="ts">
const search = ref<string>('');
const page = ref(0);
const boards = ref<Board[]>([]);
const isLast = ref<boolean>(false);

boards.value = [...boards.value, ...messageBox.body];
isLast.value = messageBox.isLast;
page.value++;
</script>
```