<template>
  <v-app id="faffy">
    <Header />
    <auth-view />
    <!--<router-view></router-view>-->
    <Footer />
  </v-app>
</template>

<script>
import Header from './components/common/Header.vue'
//import ProfileView from './views/ProfileView.vue'
import AuthView from './views/AuthView.vue'
import Footer from './components/common/Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    AuthView,
    Footer
  }
}
</script>

<style>
#faffy {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: calc(100vw - (100vw - 100%));
  height: 100vh;
  text-align: center;
  background-color: #afb2b5;
}
</style>
