import Vue from 'vue'
import VueRouter from 'vue-router'
import UserMenu from '../views/menu.vue'
import OpenChat from '../views/chat.vue'
import Chatlist from '@/views/chatList.vue'
import Helplinelist from '../views/helplinelist.vue'

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
    component: Chatlist
  },
  {
    path: '/helplinelist',
    name: 'openHelplinelist',
    component: Helplinelist
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
