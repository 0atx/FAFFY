<template>
  <v-container class="grey lighten-5">
    <div class="d-flex justify-start">
      <v-btn text color="#0c0f66" @click="moveToBoard"><v-icon>mdi-arrow-left</v-icon>게시판으로 돌아가기</v-btn>
    </div>
    <div>
      <h1>글 수정</h1>
    </div>
    <CreateBoardForm :board="currentBoard" action="update"/>
  </v-container>
</template>

<script>
import CreateBoardForm from '@/components/board/CreateBoardForm.vue'
import { mapGetters } from 'vuex'
const boardStore = "boardStore"
const authStore = "authStore"

export default {
  name: 'UpdateBoardView',
  components: {
    CreateBoardForm
  },
  computed: {
    ...mapGetters(boardStore, ['currentBoard']),
    ...mapGetters(authStore, ['checkUserInfo']),
  },
  methods: {
    // 게시판으로 이동
    moveToBoard() {
      this.$router.push({ name: 'board' })
    }
  },
  created() {
    if (this.checkUserInfo.no !== this.currentBoard.user.no) {
      alert('게시글은 작성자만 수정할 수 있습니다.')
      this.$router.push({ name: 'board-detail', params: { boardNo: this.$route.params.boardNo }})
    }
  }
}
</script>

<style>

</style>
