import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '@/views/HomeView.vue';
import MultiplicationView from "@/views/MultiplicationView.vue";
import DivisionView from "@/views/DivisionView.vue";

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView
  },
  {
    path: '/multiplication',
    name: 'Multiplication',
    component: MultiplicationView
  },
  {
    path: '/division',
    name: 'Division',
    component: DivisionView
  }
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
});

export default router;
