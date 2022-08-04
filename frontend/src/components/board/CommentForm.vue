<!--
  작성자: 류경하
  설명: 댓글 작성
  최종 수정일: 2022.08.04
-->
<template>
  <v-container>
    <v-row>
      <v-col cols="8">
        <!-- 댓글 작성 -->
        <v-textarea
          clearable
          clear-icon="mdi-close-circle"
          label="Text"
          v-model="comment"
          value="This is clearable text.">
        </v-textarea>
      </v-col>
      <!-- 댓글 작성 버튼 -->
      <v-col cols="4">
        <v-btn color="warning" @click="submitComment(comment)">등록</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
const authStore = "authStore"
const boardStore = "boardStore"

export default {
  name: 'CommentForm',
  data() {
    return {
      boardNo: this.$route.params.boardNo,
      comment: '',
    }
  },
  computed: {
    ...mapGetters(authStore, ['checkUserInfo'])
  },
  methods: {
    ...mapActions(boardStore, ['createComment']),
    submitComment(comment) {
      if (!this.comment) {
        alert('내용을 입력하세요.')
      } else {
        const commentForm = {
          board_no: this.boardNo,
          writer_no: this.checkUserInfo.no,
          content: comment,
        }
        console.log(commentForm)
        this.createComment(commentForm)
        this.$router.go(this.$router.currentRoute)
      }
    }
  }
}
</script>

<style>

</style>
