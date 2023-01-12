import Vue from 'vue'
import VueRouter from 'vue-router'
import UserMenu from '../views/menu.vue'
import OpenChat from '../views/chat.vue'
import Chatlist from '@/views/chatlist.vue'
import Administration from '../views/administration.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'UserMenu',
    component: UserMenu,
  },
  {
    path: '/chat',
    name: 'OpenChat',
    component: OpenChat
  },
  {
    path: '/chatlist',
    name: 'openChatList',
    component: Chatlist
  },
  {
    path: '/administration',
    name: 'openAdministration',
    component: Administration
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
