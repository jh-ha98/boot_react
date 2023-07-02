<template>
  <RouterView />
</template>

<script setup lang="ts">
import { RouterView } from 'vue-router';

</script>

<style lang="scss">
html,
body {
  margin: 0;
  padding: 0;
  width: 100vw;
  height: 100vh;
}

#app {
  height: 100vh;
  width: 100vw;

  /* 컨테이너 공간 맞추기 */
  display: flex;

  /*flex-direction : row (기본값, 가로가 중심축)  */
  /*flex-direction : column (세로가 중심축, 위에서 아래로)  */
  flex-direction: column;
}

button {
  cursor: pointer;
}

h2 {
  font-size: 2rem;
}

.default-button {
  box-sizing: border-box;
  padding: .5rem;
  border-radius: .5rem;
  border: 0;
  flex: .18;
  background-color: rgb(209, 191, 245);
  color: rgb(103, 64, 182);
  font-weight: bolder;
  box-shadow: 0 0 0 0 rgb(103, 64, 182);
  transition: all .2s;

  &:hover {
    box-shadow: 0 0 0 .2rem rgb(103, 64, 182);
  }
}
</style>
