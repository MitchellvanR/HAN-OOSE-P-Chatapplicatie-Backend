import Vue from 'vue'
import VueRouter from 'vue-router'
import UserMenu from '../views/menu.vue'
import OpenChat from '../views/chat.vue'
import Chatlist from "@/views/chatList.vue";

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
    name: 'openChatlist',
    component: Chatlist
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
