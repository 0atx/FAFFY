<template>
  <v-app id="faffy">
    <Header />
    <sign-in />
    <!--
    <HelloWorld msg="Welcome to Your Vue.js App"/>
    <Footer />
    -->
  </v-app>
</template>

<script>
import Header from './components/common/Header.vue'
import SignIn from './components/user/SignIn.vue'
//import HelloWorld from './components/HelloWorld.vue'
//import Footer from './components/common/Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    SignIn
  }
}
</script>

<style>
#faffy {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>
