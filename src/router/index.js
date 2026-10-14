import { createRouter, createWebHistory } from 'vue-router'
import Home from '../pages/Home.vue'
import Friends from '../pages/Friends.vue'
import Notifications from '../pages/Notifications.vue'
import Settings from '../pages/Settings.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/friends', name: 'Friends', component: Friends },
  { path: '/notifications', name: 'Notifications', component: Notifications },
  { path: '/settings', name: 'Settings', component: Settings }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
