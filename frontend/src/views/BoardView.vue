<template>
  <v-container class="grey lighten-5" fluid>
    <v-row>
      <v-col
      cols="3">
        <SideBar @changeBoardType="changeType"/>
      </v-col>
      <v-col
      cols="9">
        <ArticleList :type="boardType" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import SideBar from '@/components/board/SideBar.vue'
import ArticleList from '@/components/board/ArticleList.vue'
import { mapActions } from 'vuex'
const boardStore = "boardStore";

export default {
  name: 'BoardView',
  components: {
    SideBar,
    ArticleList,
  },
  data() {
    return {
      boardType: '자유',
    }
  },
  methods: {
    ...mapActions(boardStore, ['fetchArticles']),
    changeType(type) {
      this.boardType = type
      console.log(type)
    },
  },
  created() {
    this.fetchArticles()
  }
}
</script>

<style>

</style>
