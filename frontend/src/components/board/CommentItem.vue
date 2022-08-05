<!--
  작성자: 류경하
  설명: 각각의 댓글
  최종 수정일: 2022.08.05
-->
<template>
  <v-container fluid>
    <v-card
      outlined
      class="mx-auto text-left"
    >
    <!-- 댓글 내용 -->
      <v-card-text>
        <div>{{ comment.writer.nickname }}</div>
        <div class="text--primary">
          {{ comment.content}}
        </div>
      </v-card-text>
    <!-- 수정, 삭제 버튼 -->
      <v-card-actions>
        <v-btn
          text
          color="deep-purple accent-2"
        >
          수정
        </v-btn>
        <v-btn
          text
          color="error accent-2"
          @click="requestDeleteComment(comment.no)"
        >
          삭제
        </v-btn>
        <p>작성일: {{ comment.datetime }}</p>
        <p>정보: {{ comment }}</p>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import { mapActions } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'CommentItem',
  props: {
    comment: Object,
  },
  methods: {
    ...mapActions(boardStore, ['deleteComment']),
    requestDeleteComment(commentNo) {
      if (confirm('정말 삭제하시겠습니까?')) {
        console.log(`${commentNo}번 댓글을 삭제합니다.`)
        this.deleteComment(commentNo)
      }
    }
  }
}
</script>

<style>

</style>
