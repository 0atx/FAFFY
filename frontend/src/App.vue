<template>
  <v-app id="faffy">
    <Header />
    <router-view></router-view>
    <Footer />
  </v-app>
</template>

<script>
import Header from '@/components/common/Header.vue'
import Footer from '@/components/common/Footer.vue'

export default {
  name: 'App',
  components: {
    Header,
    Footer
  }
}
</script>

<style>
@font-face {
  font-family: 'SUIT-Medium';
  src: url(@/assets/fonts/SUIT-Medium.ttf);
}
#faffy {
  font-family: SUIT-Medium, Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: calc(100vw - (100vw - 100%));
  text-align: center;
  background-color: #afb2b5;
}
</style>
