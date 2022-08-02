<template>
  <v-container class="grey lighten-5">
    <div>
      <h1>글 작성</h1>
      <v-btn @click="moveToBoard">게시판으로 돌아가기</v-btn>
    </div>
    <CreateArticleForm />
  </v-container>
</template>

<script>
import CreateArticleForm from '@/components/board/CreateArticleForm.vue'

export default {
  name: 'CreateArticleView',
  components: {
    CreateArticleForm
  },
  methods: {
    moveToBoard() {
      this.$router.push({ name: 'board' })
    }
  }
}
</script>

<style>

</style>
