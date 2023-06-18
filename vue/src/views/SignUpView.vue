<template>
  <article>
    <section>
      <div class="wrap">
        <form v-on:submit="onClickSignUp">
          <div>
            <label>ID</label>
            <div class="id-box">
              <input type="text" class="id-input" v-model="loginId" autofocus />
              <button class="check-id-button" @click="onClickCheckId">중복확인</button>
            </div>
            <label>Password</label>
            <input type="password" class="input-box" v-model="password" />
            <label>Email</label>
            <input type="email" class="input-box" v-model="email" />
          </div>
          <button class="submit-button" @click="onClickSignUp">회원가입</button>
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
  email: string;
} | null;

const memberRef = ref<Member>(null);
const loginId = ref<string>('');
const password = ref<string>('');
const email = ref<string>('');
const checked = ref(false);

const onClickSignUp = (e: Event) => {
  e.preventDefault();

  if (!checked.value) {
    console.log(checked.value);
    alert("중복확인 먼저 해주세요.");
    return;
  }

  const params = {
    loginId: loginId.value,
    password: password.value,
    email: email.value,
  };

  axios.post('/api/member/sign-up', params)
    .then((res) => {
      const member = res.data.body;
      alert(res.data.msg);
      memberRef.value = member;

      if (member) {
        router.push({ path: '/user/sign-in', replace: true });
      }
    })
    .catch((err) => {
      console.error(err);
      alert(err.response.data.msg);
    });
};

const onClickCheckId = () => {
  const param = {
    loginId: loginId.value,
  };

  axios.get('/api/member/check-id', { params: param })
    .then((res) => {
      console.log(res);
      checked.value = true;
      alert(res.data.msg);
    })
    .catch((err) => {
      console.error(err);
      alert(err.response.data.msg);
    });
};

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

.wrap {
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

.id-box {
  display: flex;
  justify-content: space-between;
}

.id-input {
  display: block;
  width: 100%;
  box-sizing: border-box;
  padding: .5rem;
  border-radius: .5rem;
  border: 0;
  flex: .8;
}

.check-id-button {
  border: 0;
  flex: .18;
  background-color: rgb(209, 191, 245);
  color: rgb(103, 64, 182);
  font-weight: bolder;
  box-shadow: 0 0 0 0 rgb(103, 64, 182);
  transition: all .2s;
  border-radius: .5rem;

  &:hover {
    box-shadow: 0 0 0 .2rem rgb(103, 64, 182);
  }
}

.input-box {
  display: block;
  width: 100%;
  box-sizing: border-box;
  padding: .5rem;
  border-radius: .5rem;
  border: 0;
}

.submit-button {
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