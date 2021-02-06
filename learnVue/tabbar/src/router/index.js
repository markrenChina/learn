import { createRouter, createWebHistory } from 'vue-router'

const index = ()=> import("../views/index/index.vue")
const about = ()=> import("../views/about/about.vue")
const user = ()=> import("../views/user/user.vue")
const shop = ()=> import("../views/shop/shop.vue")

const routes = [
  {
    path: '/',
    redirect: '/index'
  },
  {
    path: "/index",
    name: "index",
    component: index
  },
  {
    path: "/about",
    name: "about",
    component: about
  },
  {
    path: "/user",
    name: "user",
    component: user
  },
  {
    path: "/shop",
    name: "shop",
    component: shop
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
