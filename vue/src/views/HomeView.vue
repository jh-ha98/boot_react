<template>
  <header>
    <div class="top-bar-wrap">
      <div class="menu-wrap">
        <img :src="menuImg" class="menu-img" @click="onClickMenu" />
        <div>logo</div>
      </div>
      <div>
        <RouterLink v-if="member === null" to="/user/sign-in">
          <button class="default-button login-button">로그인</button>
        </RouterLink>

        <RouterLink v-if="member === null" to="/user/sign-up">
          <button class=" default-button">회원가입</button>
        </RouterLink>
        {{ !(member === null) ? member.loginId + '님 환영합니다' : '' }}
        <button v-if="!(member === null)" class="default-button logOut-button" @click="onClickSignOut">로그아웃</button>
      </div>
    </div>
  </header>
  <section class="outer-section">
    <nav :class="{ show: sideMenuIsShow, hide: !sideMenuIsShow }">
      <RouterLink to="/" class="link">
        <img :src="homeImg" class="home-img" /> 홈
      </RouterLink>
      <RouterLink to="/board/list" class="link">
        <img :src="boardImg" class="board-img" /> 게시판
      </RouterLink>
    </nav>
    <section class="innser-section">
      <router-view></router-view>
    </section>
  </section>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { RouterLink } from 'vue-router';
import axios from 'axios';
import menuImg from "../assets/img/menu.png";
import homeImg from "../assets/img/home.png";
import boardImg from "../assets/img/board.png";

type Member = { loginId: string; } | null;

const member = ref<Member>(null);
const sideMenuIsShow = ref(true);

onMounted(() => {
  axios.get('/api/member/info')
    .catch((err) => err.response)
    .then((res) => {
      console.log(res.data?.body);
      member.value = res.data?.body;
    })
    .catch((err) => console.log(err));
});

const onClickMenu = () => {
  sideMenuIsShow.value = !sideMenuIsShow.value;
};

const onClickSignOut = () => {
  axios.delete('/api/member/sign-out')
    .then((res) => {
      console.log(res.data);
      alert(res.data?.msg);
      member.value = res.data?.body;
    })
    .catch((err) => {
      console.error(err);
    });
};
</script>

<style scoped lang="scss">
.link {
  padding: 1rem;
  font-weight: bolder;
  text-decoration-line: none;
  display: block;

  &:hover {
    background-color: white;
  }
}

header {
  background-color: mediumpurple;
  display: flex;
  height: 3rem;
  justify-content: center;
  flex-direction: column;
  color: whitesmoke;
}

.top-bar-wrap {
  display: flex;

  /* 아이템 사이에 간격 맞추기 */
  justify-content: space-between;
  margin: 1rem;
}

.menu-wrap {
  display: flex;
  align-items: center;
}

.login-button {
  margin: 0 0.3rem;
}

.logOut-button {
  margin-left: .3rem;
}

.outer-section {
  flex: 1;
  display: flex;
  overflow: hidden;
}

.innser-section {
  flex: 1;
  overflow: auto;
  padding: .7rem;
}

nav {
  background-color: rgb(177, 149, 233);
  white-space: nowrap;
  overflow: hidden;
  height: 100%;
}

nav.show {
  background-color: rgb(177, 149, 233);
  width: 15rem;
}

nav.hide {
  background-color: rgb(177, 149, 233);
  width: calc(1.4rem + 2 * 0.1rem + 2 * 1rem);
  white-space: nowrap;
  overflow-x: hidden;
}

.menu-img {
  width: 1.4rem;
  height: 1.4rem;
  margin-right: 1.5rem;
  cursor: pointer;
}

.home-img {
  width: 1.4rem;
  height: 1.4rem;
  vertical-align: text-bottom;
  margin-right: 1.5rem;
}

.board-img {
  width: 1.4rem;
  height: 1.4rem;
  vertical-align: text-bottom;
  margin-right: 1.5rem;
}
</style>