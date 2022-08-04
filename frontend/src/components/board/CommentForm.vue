<!--
  작성자: 류경하
  설명: 댓글 작성
  최종 수정일: 2022.08.03
-->
<template>
  <v-container>
    <v-row>
      <v-col cols="8">
        <v-textarea
          clearable
          clear-icon="mdi-close-circle"
          label="Text"
          v-model="comment"
          value="This is clearable text.">
        </v-textarea>
      </v-col>
      <v-col cols="4">
        <v-btn color="warning" @click="submitComment(comment)">등록</v-btn>
        <p>{{ checkUserInfo }}</p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
const commentStore = "commentStore"
const authStore = "authStore"

export default {
  name: 'CommentForm',
  data() {
    return {
      articleNo: this.$route.params.articleNo,
      comment: '',
    }
  },
  computed: {
    ...mapGetters(authStore, ['checkUserInfo'])
  },
  methods: {
    ...mapActions(commentStore, ['createComment']),
    submitComment(comment) {
      const commentForm = {
        board_no: this.articleNo,
        writer_no: this.checkUserInfo.no,
        content: comment,
      }
      console.log(commentForm)
      this.createComment(commentForm)
    }
  }
}
</script>

<style>

</style>
