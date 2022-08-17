<!--
  작성자: 류경하
  설명: 댓글 작성
  최종 수정일: 2022.08.11
  최종 수정자: 박윤하
-->
<template>
  <v-container class="ml-2">
    <div>
      <v-form ref="form">
        <v-row class="mx-auto" style="width:80%;">
          <v-col class="pb-0" cols="12">
            <!-- 댓글 작성 -->
            <v-textarea
              style="background-color:white"
              outlined
              clearable
              clear-icon="mdi-close-circle"
              placeholder="댓글을 입력하세요."
              no-resize
              color="#0c0f66"
              maxlength="200"
              v-model="comment"
              hide-details
              rows="3">
            </v-textarea>
          </v-col>
        </v-row>
        <v-row class="mx-auto" style="width:80%;text-align:end">
          <!-- 댓글 작성 버튼 -->
          <v-col cols="12">
            <v-btn id="commentBtn" style="height:38px; width:80px;" @click="submitComment(comment)">등록</v-btn>
          </v-col>
        </v-row>
      </v-form>
    </div>
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
    ...mapGetters(authStore, ['checkUserInfo']),
  },
  methods: {
    ...mapActions(boardStore, ['createComment']),
    submitComment(comment) {
      const validate = this.$refs.form.validate();

      if (!this.comment) {
        this.$dialog.message.info('내용을 입력하세요.', {
          position: "top",
          timeout: 2000,
          color: "#ff7451",
        });
      } else if(validate) {
        const commentForm = {
          board_no: this.boardNo,
          writer_no: this.checkUserInfo.no,
          content: comment,
        }
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

::v-deep ::-webkit-scrollbar {
  width: 0;
  background: transparent;
}
</style>
