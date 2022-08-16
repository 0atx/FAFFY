import Vue from 'vue'
import App from './App.vue'
import VueMeta from 'vue-meta'
import vuetify from './plugins/vuetify'
import Carousel3d from 'vue-carousel-3d'
import VuetifyDialog from 'vuetify-dialog'
import 'vuetify-dialog/dist/vuetify-dialog.css'
import router from './router'
import store from "./store";
import axios from 'axios'

Vue.use(VueMeta)
Vue.use(Carousel3d)
Vue.use(VuetifyDialog, {
  context: {
    vuetify
  }
})

Vue.prototype.$axios = axios
Vue.config.productionTip = false

new Vue({
  vuetify,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
