<!--
  작성자: 류경하
  설명: 게시판 글 상세조회
  최종 수정일: 2022.08.03
-->
<template>
  <!-- <v-container fluid>
    <p class="content">제목: {{ currentBoard.title }}</p>
    <p class="content" @click="moveProfile(currentBoard.user.no)" style="cursor: pointer">작성자: {{ currentBoard.user.nickname }}</p>
    <p class="content">카테고리: {{ currentBoard.category }}</p>
    <p class="content">조회수: {{ currentBoard.hit }}</p>
    <div class="content" v-if="currentImage">
      <v-img contain :src="currentImage" max-height="300px" alt=""></v-img>
    </div>
    <div class="content">내용: {{ currentBoard.content }}</div>
  </v-container> -->
  <v-container fluid>
    <v-card height="100%" width="100%"
      class="mx-auto">
      <v-card-title class="text-h4">
        {{ currentBoard.title }}
      </v-card-title>
      <v-divider></v-divider>
      <v-card-subtitle class="d-flex justify-space-between">
        <p class="content" @click="moveProfile(currentBoard.user.no)" style="cursor: pointer">작성자: {{ currentBoard.user.nickname }}</p>
        <div class="d-flex justify-end">
          <p>작성일: {{ currentBoard.dateTime.slice(0,4)+'년 '+currentBoard.dateTime.slice(5, 7)+'월 '+currentBoard.dateTime.slice(8, 10)+'일' }}</p>
          <p><v-icon>mdi-eye</v-icon> {{ currentBoard.hit }}</p>
        </div>
      </v-card-subtitle>
      <v-img
        contain :src="currentImage" max-height="300px" alt="" class="grey darken-4">
      </v-img>
      <v-card-text class="text--primary d-flex justify-start">
        {{ currentBoard.content }}
      </v-card-text>
      <v-card-actions class="d-flex justify-end">
        <v-btn text color="error" @click="requestDeleteBoard(boardNo)" :disabled="checkUserInfo.no!==currentBoard.user.no">삭제</v-btn>
        <v-btn text color="warning" @click="requestUpdateBoard(boardNo)" :disabled="checkUserInfo.no!==currentBoard.user.no">수정</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
const boardStore = "boardStore"
const authStore = "authStore"

export default {
  name: 'BoardDetail',
  data() {
    return {
      boardNo: this.$route.params.boardNo
    }
  },
  computed: {
    ...mapGetters(boardStore, ['currentBoard', 'currentImage']),
    ...mapGetters(authStore, ['checkUserInfo']),
  },
  methods: {
    moveProfile(user) {
      this.$router.push({ name: 'profile', params: { no: user }})
    },
    requestDeleteBoard(boardNo) {
      console.log(`${boardNo}번 글을 삭제합니다.`)
      if (confirm('정말로 삭제하시겠습니까?')) {
        this.deleteBoard(boardNo)
      }
    },
    requestUpdateBoard(boardNo) {
      console.log(`${boardNo}번 글을 수정합니다.`)
      this.$router.push({ name: 'board-update', params: { boardNo: boardNo }})
    }
  }
}
</script>

<style scoped>
.content {
  border: none black;
}
p {
  margin-right: 10px;
}
</style>
