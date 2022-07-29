<template>
  <div>
    <SideBar />
    <SearchBar />
    <ArticleList />
  </div>
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
