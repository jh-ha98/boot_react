import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
const SignInView = () => import('../views/SignInView.vue');
const SignUpView = () => import('../views/SignUpView.vue');
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      // children: [{
      //   path: '/board/list'
      // }]
    },
    {
      path: '/sign-in',
      name: 'sign-in',
      component: SignInView,
    },
    {
      path: '/sign-up',
      name: 'sign-up',
      component: SignUpView,
    }
  ]
})

export default router