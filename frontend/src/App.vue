<template>
  <v-app id="faffy">
    <Header :isOnAir="isOnAir" />
    <router-view></router-view>
    <Footer v-if="show" />
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
  },
  data() {
    return {
      show: false,
      isOnAir: false,
    };
  },
  watch: {
    $route(to) {
      if (
        !(
          to.name == "consulting-onair" ||
          to.name == "consulting-ready"
        )
      ) {
        this.show = true;
        this.isOnAir = false;
      } else {
        this.show = false;
        this.isOnAir = true;
      }
    },
  },
}
</script>

<style>
@font-face {
  font-family: 'SUIT-Medium';
  src: url(@/assets/fonts/SUIT-Medium.ttf);
}

#faffy {
  font-family: SUIT-Medium, sans-serif, Arial;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  width: calc(100vw - (100vw - 100%));
  text-align: center;
  background-color: #afb2b5;
}
</style>
