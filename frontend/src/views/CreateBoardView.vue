<template>
  <v-container class="grey lighten-5">
    <div>
      <h1>글 작성</h1>
      <v-btn @click="moveToBoard">게시판으로 돌아가기</v-btn>
    </div>
    <CreateBoardForm />
  </v-container>
</template>

<script>
import CreateBoardForm from '@/components/board/CreateBoardForm.vue'

export default {
  name: 'CreateBoardView',
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
