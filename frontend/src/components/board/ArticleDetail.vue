<template>
  <v-container>
    <h2>게시글 내용</h2>
    <p>제목: {{ currentArticle.title }}</p>
    <p>작성자: {{ currentArticle.writer }}</p>
    <p>조회수: {{ currentArticle.hit }}</p>
    <p>내용: {{ currentArticle.content }}</p>
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'ArticleDetail',
  data() {
    return {
      articleNo: this.$route.params.articleNo
    }
  },
  computed: {
    ...mapGetters(boardStore, ['currentArticle']),
  },
  methods: {
    ...mapActions(boardStore, ['fetchArticle']),
  },
  created() {
      this.fetchArticle(this.articleNo)
  }
}
</script>

<style scoped>
p {
  background-color: white;
}

</style>
