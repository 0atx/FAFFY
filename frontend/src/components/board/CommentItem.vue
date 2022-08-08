<!--
  작성자: 류경하
  설명: 각각의 댓글
  최종 수정일: 2022.08.05
-->
<template>
  <v-container fluid class="pa-1 border border-primary rounded-4">
    <v-card
      outlined
      class="mx-auto text-left"
    >
    <!-- 댓글 내용 -->
      <v-card-text>
        <div class="d-flex justify-space-between">
          <span class="text-h6 text--primary" style="cursor:pointer" @click="moveProfile(comment.writer.no)">
            {{ comment.writer.nickname }}
          </span>
          <span>{{ comment.datetime.slice(0,4)+'년 '+comment.datetime.slice(5, 7)+'월 '+comment.datetime.slice(8, 10)+'일' }}</span>
        </div>
        <hr inset>
        <div class="text--primary">
          {{ comment.content}}
        </div>
      </v-card-text>
    <!-- 삭제 버튼(댓글작성자일 때만 활성화) -->
      <v-card-actions>
        <v-btn
          text
          color="error accent-2"
          @click="requestDeleteComment(comment.no)"
          :disabled="comment.writer.nickname !== checkUserInfo.nickname">
          삭제
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
const boardStore = "boardStore"
const authStore = "authStore"

export default {
  name: 'CommentItem',
  props: {
    comment: Object,
  },
  computed: {
    ...mapGetters(authStore, ['checkUserInfo']),
  },
  methods: {
    ...mapActions(boardStore, ['deleteComment']),
    requestDeleteComment(commentNo) {
      if (confirm('정말 삭제하시겠습니까?')) {
        console.log(`${commentNo}번 댓글을 삭제합니다.`)
        this.deleteComment(commentNo)
      }
    },
    moveProfile(userNo) {
      console.log(userNo)
      this.$router.push({ name: 'profile', params: { no: userNo }})
    }
  }
}
</script>

<style scoped>
</style>
