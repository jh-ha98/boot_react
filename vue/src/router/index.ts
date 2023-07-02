import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue';
import BoardListView from '../views/BoardListView.vue';
import BoardWriteView from '../views/BoardWriteView.vue';
import BoardDetailView from '../views/BoardDetailView.vue';
const SignInView = () => import('../views/SignInView.vue');
const SignUpView = () => import('../views/SignUpView.vue');
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children: [
        {
          path: '/board/list',
          name: 'board-list',
          component: BoardListView,
        },
        {
          path: '/board/list/:boardId',
          name: 'board-detail',
          component: BoardDetailView,
        },
        {
          path: '/board/write',
          name: 'board-write',
          component: BoardWriteView,
        }
      ]
    },
    {
      path: '/user/sign-in',
      name: 'sign-in',
      component: SignInView,
    },
    {
      path: '/user/sign-up',
      name: 'sign-up',
      component: SignUpView,
    }
  ]
})

export default router