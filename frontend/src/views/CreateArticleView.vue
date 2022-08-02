<template>
  <v-container class="grey lighten-5">
    <h1>글 작성</h1>
    <CreateArticleForm />
  </v-container>
</template>

<script>
import CreateArticleForm from '@/components/board/CreateArticleForm.vue'

export default {
  name: 'CreateArticleView',
  components: {
    CreateArticleForm
  }
}
</script>

<style>

</style>
