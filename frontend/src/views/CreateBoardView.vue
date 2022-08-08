<template>
  <v-container class="grey lighten-5">
    <div class="d-flex justify-start">
      <v-btn text color="#0c0f66" @click="moveToBoard"><v-icon>mdi-arrow-left</v-icon>게시판으로 돌아가기</v-btn>
    </div>
    <div>
      <h1>글 작성</h1>
    </div>
    <CreateBoardForm :board="board" action="create"/>
  </v-container>
</template>

<script>
import CreateBoardForm from '@/components/board/CreateBoardForm.vue'

export default {
  name: 'CreateBoardView',
  data() {
    return {
      board: {
        title: '',
        content: '',
        category: '',
        img: null
      }
    }
  },
  components: {
    CreateBoardForm
  },
  methods: {
    // 게시판으로 이동
    moveToBoard() {
      this.$router.push({ name: 'board' })
    }
  }
}
</script>

<style>

</style>
