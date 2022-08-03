<template>
  <v-container>
    <h1>게시글 상세보기</h1>
    <ArticleDetail />
    <CommentForm />
  </v-container>
</template>

<script>
import ArticleDetail from '@/components/board/ArticleDetail.vue'
import CommentForm from '@/components/comment/CommentForm.vue'

export default {
  name: 'ArticleDeatilView',
  components: {
    ArticleDetail,
    CommentForm,
  }
}
</script>

<style>

</style>
