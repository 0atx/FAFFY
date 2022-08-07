<!--
  작성자: 류경하
  설명: 게시판 글 상세조회
  최종 수정일: 2022.08.03
-->
<template>
  <v-container>
    <h2>게시글 내용</h2>
    <p>제목: {{ currentBoard.title }}</p>
    <p>작성자: {{ currentBoard.user.nickname }}</p>
    <p>카테고리: {{ currentBoard.category }}</p>
    <p>조회수: {{ currentBoard.hit }}</p>
    <p>내용: {{ currentBoard.content }}</p>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'BoardDetail',
  data() {
    return {
      boardNo: this.$route.params.boardNo
    }
  },
  computed: {
    ...mapGetters(boardStore, ['currentBoard']),
  },
  created() {
    console.log('해위', this.currentBoard.content)
  }
}
</script>

<style scoped>
p {
  background-color: white;
}

</style>
