<!--
  작성자: 류경하
  설명: 댓글 목록
  최종 수정일: 2022.08.11
  최종 수정자: 박윤하
-->
<template>
  <v-container class="ml-2">
    <!-- <h1>댓글 목록</h1> -->
    <CommentItem
      v-for="comment in commentList"
      :key="comment.no"
      :comment="comment"
      />
  </v-container>
</template>

<script>
import CommentItem from '@/components/board/CommentItem.vue'
import { mapGetters } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'CommentList',
  components: {
    CommentItem,
  },
  computed: {
    ...mapGetters(boardStore, ['commentList'])
  }
}
</script>

<style scoped>

</style>
