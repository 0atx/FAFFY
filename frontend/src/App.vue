<template>
  <v-app id="faffy">
    <Header />
    <profile-view />
    <!--<router-view></router-view>-->
    <Footer />
  </v-app>
</template>

<script>
import Header from './components/common/Header.vue'
import ProfileView from './views/ProfileView.vue'
import Footer from './components/common/Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    Footer,
    ProfileView
  }
}
</script>

<style>
#faffy {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: calc(100vw - (100vw - 100%))px;
  text-align: center;
  background-color: #2c3e50;
}
</style>
