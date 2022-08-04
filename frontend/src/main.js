import Vue from 'vue'
import App from './App.vue'
import VueMeta from 'vue-meta'
import vuetify from './plugins/vuetify'
import Carousel3d from 'vue-carousel-3d'
import router from './router'
import store from "./store";

Vue.use(VueMeta)
Vue.use(Carousel3d)

Vue.config.productionTip = false

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
