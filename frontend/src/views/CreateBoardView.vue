<template>
  <v-container fluid>
    <v-row style="padding-left: 14%; background-color:#0c0f66; color: #fff" class="text-left mt-5 pt-5">
      <h1>글 작성</h1>
    </v-row>
    <v-row style="padding-left: 14%; background-color:#0c0f66; color: #fff" class="text-left mb-5 pb-5">
      <h4>타 회원을 향한 비방, 욕설, 스팸 등 부적절한 내용 발견 시 무통보 삭제 및 회원 탈퇴 처리가 될 수 있습니다.</h4>
    </v-row>
    <v-container>
      <CreateBoardForm :board="board" action="create"/>
    </v-container>
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
