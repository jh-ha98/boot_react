<template>
  <article>
    <section>
      <div class="div-wrap">
        <form v-on:submit="onClickLogin">
          <div>
            <label>ID</label>
            <input type="text" v-model="loginId" />
            <label>Password</label>
            <input type="password" v-model="password" />
          </div>
          <button>로그인</button>
        </form>
      </div>
    </section>
  </article>
</template>

<script setup lang="ts">
import router from '@/router';
import axios from 'axios';
import { ref } from 'vue';

type Member = {
  loginId: string;
  password: string;
} | null;

const memberRef = ref<Member>(null);
const loginId = ref<string>();
const password = ref<string>();

const onClickLogin = (e: Event) => {
  e.preventDefault();

  const param = {
    loginId: loginId.value,
    password: password.value
  };

  console.log(param)

  axios.post('/api/member/sign-in', param)
    .then((res) => {
      const member = res.data.body;
      alert(res.data.msg);
      memberRef.value = member;

      if (member) {
        router.push({ path: '/', replace: true });
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
  height: 100%;
  display: flex;
  justify-content: center;
  flex-direction: column;
}

section {
  display: flex;
  flex-direction: column;
  justify-content: center;
  height: 100%;
  padding: .5rem;
  background-color: rgb(210, 192, 247);
}

.div-wrap {
  display: flex;
  justify-content: center;
}

form {
  height: 20rem;
  padding: 2rem;
  width: 25rem;
  background-color: mediumpurple;
  border-radius: 1rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  box-shadow: 0 14px 28px rgba(0, 0, 0, .4);
}

label {
  margin: 0;
  padding: .3rem;
  display: block;
  font-weight: bolder;
  color: rgb(209, 191, 245);
}

input {
  display: block;
  width: 100%;
  box-sizing: border-box;
  padding: .5rem;
  border-radius: .5rem;
  border: 0;
}

button {
  width: 100%;
  padding: .5rem;
  border-radius: 1rem;
  border: 0;
  background-color: rgb(209, 191, 245);
  color: rgb(103, 64, 182);
  font-weight: bolder;
  font-size: 2rem;
  box-shadow: 0 .4rem rgb(103, 64, 182);
  transition: all .2s;

  &:hover {
    box-shadow: 0 0 rgb(103, 64, 182);
    transform: translateY(.4rem);
    cursor: pointer;
  }
}
</style>