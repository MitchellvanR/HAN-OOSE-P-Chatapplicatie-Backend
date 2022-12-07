import Vue from 'vue'
import VueRouter from 'vue-router'
import UserMenu from '../views/UserMenu.vue'
import OpenChat from '../views/OpenChat.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'UserMenu',
    component: UserMenu
  },
  {
    path: '/chat',
    name: 'OpenChat',
    component: OpenChat
  }
]

const router = new VueRouter({
  mode: 'history',
  routes
})

export default router
