<!--
  작성자: 류경하
  설명: 댓글 작성
  최종 수정일: 2022.08.05
-->
<template>
  <v-container fluid>
    <v-row>
      <v-col cols="10">
        <!-- 댓글 작성 -->
        <v-textarea
          clearable
          outlined
          clear-icon="mdi-close-circle"
          label="Text"
          v-model="comment"
          hide-details
          rows="3"
          value="This is clearable text.">
        </v-textarea>
      </v-col>
      <!-- 댓글 작성 버튼 -->
      <v-col cols="2">
        <v-btn id="commentBtn" height="100%" width="100%" @click="submitComment(comment)">등록</v-btn>
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
      }
    }
  }
}
</script>

<style scoped>
#commentBtn {
  background-color: #0c0f66;
  color: white;
}

</style>
