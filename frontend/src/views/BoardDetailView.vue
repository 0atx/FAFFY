<template>
  <v-container class="grey lighten-5">
    <h1>게시글 상세보기</h1>
    <v-btn color="success" @click="moveToBoard">게시판으로 돌아가기</v-btn>
    <v-btn color="error" @click="requestDeleteBoard(boardNo)">게시글 삭제하기</v-btn>
    <v-btn color="warning" @click="requestUpdateBoard(boardNo)">게시글 수정하기</v-btn>
    <BoardDetail />
    <CommentForm />
    <CommentList />
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'
import BoardDetail from '@/components/board/BoardDetail.vue'
import CommentForm from '@/components/board/CommentForm.vue'
import CommentList from '@/components/board/CommentList.vue'
const boardStore = "boardStore"

export default {
  name: 'BoardDeatilView',
  components: {
    BoardDetail,
    CommentForm,
    CommentList,
  },
  data() {
    return {
      boardNo: this.$route.params.boardNo
    }
  },
  methods: {
    ...mapActions(boardStore, ['fetchBoard', 'deleteBoard']),
    // 게시판으로 이동
    moveToBoard() {
      this.$router.push({ name: 'board' })
    },
    // 게시글 삭제
    requestDeleteBoard(boardNo) {
      console.log(`${boardNo}번 글을 삭제합니다.`)
      if (confirm('정말로 삭제하시겠습니까?')) {
        this.deleteBoard(boardNo)
      }
    },
    requestUpdateBoard(boardNo) {
      console.log(`${boardNo}번 글을 수정합니다.`)
      this.$router.push({ name: 'board-update', params: { boardNo: boardNo }})
    }
  },
  created() {
    this.fetchBoard(this.boardNo)
  }
}
</script>

<style>

</style>
