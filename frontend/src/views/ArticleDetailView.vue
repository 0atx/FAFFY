<template>
  <v-container>
    <h1>게시글 상세보기</h1>
    <ArticleDetail />
  </v-container>
</template>

<script>
import ArticleDetail from '@/components/board/ArticleDetail.vue'

export default {
  name: 'ArticleDeatilView',
  components: {
    ArticleDetail
  }
}
</script>

<style>

</style>
