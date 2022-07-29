<template>
  <v-container class="grey lighten-5" fluid>
    <v-row>
      <v-col
      cols="3">
        <SideBar />
      </v-col>
      <v-col
      cols="9">
        <SearchBar />
        <ArticleList />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import SideBar from '@/components/board/SideBar.vue'
import SearchBar from '@/components/board/SearchBar.vue'
import ArticleList from '@/components/board/ArticleList.vue'

export default {
  name: 'BoardView',
  components: {
    SideBar,
    SearchBar,
    ArticleList,
  }
}
</script>

<style>

</style>
