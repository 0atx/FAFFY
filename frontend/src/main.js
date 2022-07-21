import Vue from 'vue'
import App from './App.vue'
import VueMeta from 'vue-meta'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import vuetify from './plugins/vuetify'

Vue.use(BootstrapVue)
Vue.use(VueMeta)

Vue.config.productionTip = false

new Vue({
  vuetify,
  render: h => h(App)
}).$mount('#app')
