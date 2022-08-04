<template>
  <v-container class="grey lighten-5">
    <h1>게시글 상세보기</h1>
    <v-btn color="success" @click="moveToBoard">게시판으로 돌아가기</v-btn>
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
    ...mapActions(boardStore, ['fetchBoard']),
    // 게시판으로 이동
    moveToBoard() {
      this.$router.push({ name: 'board' })
    }
  },
  created() {
    this.fetchBoard(this.boardNo)
  }
}
</script>

<style>

</style>
