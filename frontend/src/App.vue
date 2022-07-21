<template>
  <v-app id="faffy">
    <Header />
    <sign-up />
    <!--
    <HelloWorld msg="Welcome to Your Vue.js App"/>
    <Footer />
    -->
  </v-app>
</template>

<script>
import Header from './components/common/Header.vue'
//import SignIn from './components/user/SignIn.vue'
import SignUp from './components/user/SignUp.vue'
//import HelloWorld from './components/HelloWorld.vue'
//import Footer from './components/common/Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    SignUp
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
