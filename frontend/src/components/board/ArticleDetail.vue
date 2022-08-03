<!--
  작성자: 류경하
  설명: 게시판 글 상세조회
  최종 수정일: 2022.08.03
-->
<template>
  <v-container>
    <h2>게시글 내용</h2>
    <p>제목: {{ currentArticle.title }}</p>
    <p>작성자: {{ currentArticle.user.nickname }}</p>
    <p>카테고리: {{ currentArticle.category }}</p>
    <p>조회수: {{ currentArticle.hit }}</p>
    <p>내용: {{ currentArticle.content }}</p>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
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
}
</script>

<style scoped>
p {
  background-color: white;
}

</style>
