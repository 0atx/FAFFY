<template>
  <v-container class="grey lighten-5">
    <h1>게시글 상세보기</h1>
    <ArticleDetail />
    <CommentForm />
    <CommentList />
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'
import ArticleDetail from '@/components/board/ArticleDetail.vue'
import CommentForm from '@/components/board/CommentForm.vue'
import CommentList from '@/components/board/CommentList.vue'
const boardStore = "boardStore"

export default {
  name: 'ArticleDeatilView',
  components: {
    ArticleDetail,
    CommentForm,
    CommentList,
  },
  data() {
    return {
      articleNo: this.$route.params.articleNo
    }
  },
  methods: {
    ...mapActions(boardStore, ['fetchArticle']),
  },
  created() {
    this.fetchArticle(this.articleNo)
  }
}
</script>

<style>

</style>
