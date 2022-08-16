<!--
  작성자: 류경하
  설명: 게시판 글 상세조회
  최종 수정일: 2022.08.11
  최종 수정자: 박윤하
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
  <v-container class="ml-2">
    <div>
      <div class="mb-4">
        <router-link to="/board" class="text-h6" style="margin-left:10%; font-weight: 600; color:black; text-decoration: none;"><v-icon class="mb-1" color="black" size="20"> mdi-arrow-left </v-icon> 게시판 목록</router-link>
      </div>
      <v-card
        width="80%"
        height="100%"
        elevation="0"
        class="mx-auto">
        <v-card-title class="mb-2" style="padding-left: 8px; font-size: 24px;">
          {{ currentBoard.title }}
        </v-card-title>
        <v-card-subtitle style="font-size: 14px; padding-left: 8px; padding-bottom:6px">
          <div style="display:flex; justify-content: space-between">
            <div  @click="moveProfile(currentBoard.user.no)" style="cursor: pointer; display:flex; align-items:center">
            <profile-img-avatar :user_no="currentBoard.user.no" />
            <div class="mr-2">{{ currentBoard.user.nickname }}</div> | <div class="ml-2">{{ date }}</div>
            </div>
            <div style="display:flex; align-items:center">
              <div class="mr-2"><v-icon small>mdi-eye</v-icon> {{ currentBoard.hit }}</div>
              <div><v-icon small> mdi-comment-processing-outline </v-icon> {{ currentBoard.commentCount }}</div>
            </div>
          </div>
        </v-card-subtitle>
        <v-divider></v-divider>
        <v-img
          v-if="currentImage" contain :src="currentImage" max-height="300px" alt="" class="grey darken-4">
        </v-img>
        <v-card-text class="text--primary d-flex justify-start">
          {{ currentBoard.content }}
        </v-card-text>
        <v-card-actions class="d-flex justify-end">
          <v-btn text color="#0c0f66" @click="requestUpdateBoard(boardNo)" :disabled="checkUserInfo.no!==currentBoard.user.no">수정</v-btn>
          <v-btn text color="#ff7451" @click="requestDeleteBoard(boardNo)" :disabled="checkUserInfo.no!==currentBoard.user.no">삭제</v-btn>
        </v-card-actions>
        <v-divider></v-divider>
      </v-card>
    </div>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import ProfileImgAvatar from '@/components/common/ProfileImgAvatar.vue'
const boardStore = "boardStore"
const authStore = "authStore"

export default {
  name: 'BoardDetail',
  components: {
    ProfileImgAvatar
  },
  data() {
    return {
      boardNo: this.$route.params.boardNo,
      date : '',
    }
  },
  computed: {
    ...mapGetters(boardStore, ['currentBoard', 'currentImage']),
    ...mapGetters(authStore, ['checkUserInfo']),
  },
  mounted() {
    this.date = this.currentBoard.dateTime.replaceAll('-', '.').replace('T', ' ').slice(0, 16);
  },
  methods: {
    ...mapActions(boardStore, ['deleteBoard']),
    moveProfile(user) {
      this.$router.push({ name: 'profile', params: { no: user }})
    },
    async requestDeleteBoard(boardNo) {
      console.log(`${boardNo}번 글을 삭제합니다.`)
      const res = await this.$dialog.confirm({
          text: '<br>정말로 삭제하시겠습니까?',
          icon: true,
          actions: {
            false : {
              text: '취소', color: '#ff7451'
            },
            true : {
              text: '확인', color: '#0c0f66'
            },
          }
        });
      if (res) {
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
.container {
  text-align: left;
}

.content {
  border: none black;
}

p {
  margin-right: 10px;
}

img {
  object-fit: cover;
}

.v-btn::before {
  background-color: transparent;
}
</style>
