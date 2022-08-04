<!--
  작성자: 류경하
  설명: 댓글 목록
  최종 수정일: 2022.08.03
-->
<template>
  <v-container fluid>
    <h1>댓글 목록</h1>
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

<style>
v-container {
  background-color: beige;
}
</style>
