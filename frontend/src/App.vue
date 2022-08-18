<template>
  <v-app id="faffy">
    <Header v-if="show" :isOnAir="isOnAir" />
    <Loading v-if="loading"/>
    <router-view v-show="!loading" :key="$route.path"></router-view>
  </v-app>
</template>

<script>
import { mapActions } from 'vuex'
import Header from '@/components/common/Header.vue'
import Loading from '@/components/common/LoadingView.vue'
const boardStore = "boardStore"

export default {
  name: 'App',
  components: {
    Header,
    Loading,
  },
  data() {
    return {
      show: true,
      isOnAir: false,
      loading: false,
    };
  },
  watch: {
    $route(to) {
      if (
        !(
          to.name == "404" ||
          to.name == "consulting-onair"
        )
      ) {
        this.show = true;
        this.isOnAir = false;
      } else {
        this.show = false;
        this.isOnAir = true;
      }

      if (
        !(
          to.name == "consulting-onair" ||
          to.name == "consulting-ready"
        )
      ) {
        this.isOnAir = false;
      } else {
        this.isOnAir = true;
        this.onLoading();
      }
    },
  },
  methods: {
    ...mapActions(boardStore, ['fetchBoardList']),
    onLoading () {
      this.loading = true
      setTimeout(() => {
        this.loading = false
      }, 1000);
    }
  },
  created() {
    this.fetchBoardList()
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
  background-color: #fff;
}
</style>
