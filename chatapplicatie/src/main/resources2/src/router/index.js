import Vue from 'vue'
import Router from 'vue-router'
import ChatApplication from "@/components/ChatApplication";
import UserMenu from "@/components/UserMenu";

Vue.use(Router)

export default new Router({
    routes: [
        {
            path: '/',
            name: 'UserMenu',
            component: UserMenu
        },
        {
            path: '/chat',
            name: 'ChatApplication',
            component: ChatApplication
        }
    ]
})