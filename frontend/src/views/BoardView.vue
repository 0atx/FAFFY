<template>
  <div>
    <SideMenu />
    <SearchBar />
    <ArticleList />
  </div>
</template>

<script>
import SideMenu from '@/components/board/SideMenu.vue'
import SearchBar from '@/components/board/SearchBar.vue'
import ArticleList from '@/components/board/ArticleList.vue'

export default {
  name: 'BoardView',
  components: {
    SideMenu,
    SearchBar,
    ArticleList,
  }
}
</script>

<style>

</style>
