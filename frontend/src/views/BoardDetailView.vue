<template>
  <v-container class="grey lighten-5" fluid>
    <div class="d-flex justify-start">
      <v-btn id="boardBtn" text color="#0c0f66" @click="moveToBoard"><v-icon>mdi-arrow-left</v-icon>게시판으로 돌아가기</v-btn>
    </div>
    <BoardDetail />
    <!-- <div id="buttonGroup" class="d-flex justify-end">
      <v-btn color="error" @click="requestDeleteBoard(boardNo)" :disabled="checkUserInfo.no!==currentBoard.user.no">삭제</v-btn>
      <v-btn color="warning" @click="requestUpdateBoard(boardNo)" :disabled="checkUserInfo.no!==currentBoard.user.no">수정</v-btn>
    </div> -->
    <CommentForm />
    <CommentList />
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import BoardDetail from '@/components/board/BoardDetail.vue'
import CommentForm from '@/components/board/CommentForm.vue'
import CommentList from '@/components/board/CommentList.vue'
const boardStore = "boardStore"
const authStore = "authStore"

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
  computed: {
    ...mapGetters(authStore, ['checkUserInfo']),
    ...mapGetters(boardStore, ['currentBoard']),
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

<style scoped>
#buttonGroup {
  margin-bottom: 10px;
  padding: 12px;
}
</style>
