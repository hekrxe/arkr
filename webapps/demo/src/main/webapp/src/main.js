// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueRouter from 'vue-router'
import VueResource from 'vue-resource'
import Customers from './components/Customers'
import About from './components/About'
import App from './App'
import Add from './components/Add'
import CustomerDetail from './components/CustomerDetail'


Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(VueResource)

const router = new VueRouter({
  model: 'history',
  routes: [
    {path: "/", component: Customers},
    {path: "/customers", component: Customers},
    {path: "/about", component: About},
    {path: "/add", component: Add},
    {path: "/customer/:id", component: CustomerDetail},
  ]
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router: router,
  template: '<App/>',
  components: {App}
})
