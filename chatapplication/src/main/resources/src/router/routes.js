import Vue from 'vue'
import VueRouter from 'vue-router'
import UserMenu from '../views/menu.vue'
import OpenChat from '../views/chat.vue'
import ChatList from '@/views/chatList.vue'
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
    path: '/chatList',
    name: 'openChatList',
    component: ChatList
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
