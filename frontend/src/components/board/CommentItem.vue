<!--
  작성자: 류경하
  설명: 각각의 댓글
  최종 수정일: 2022.08.11
  최종 수정자: 박윤하
-->
<template>
  <v-container style="width: 80%" class="pa-0 border border-primary rounded-4">
    <v-card
      elevation="0"
      class="mx-auto text-left"
    >
      <v-divider></v-divider>
      <v-card-subtitle style="font-size: 14px; padding-top: 10px; padding-left: 8px; padding-bottom:0">
        <div style="display:flex; justify-content: space-between">
          <div  @click="moveProfile(comment.writer.no)" style="cursor: pointer; display:flex; align-items:center">
          <profile-img-avatar :user_no="comment.writer.no" />
          <div class="mr-2">{{ comment.writer.nickname }}</div> | <div class="ml-2">{{ date }}</div>
          </div>
        </div>
      </v-card-subtitle>
      <!-- 댓글 내용 -->
      <v-card-text class="ml-12" style="color:black; padding-bottom:0;">
        {{ comment.content}}
      </v-card-text>
      <!-- 삭제 버튼(댓글작성자일 때만 활성화) -->
      <div style="text-align:right;" v-if="comment.writer.nickname === checkUserInfo.nickname">
        <v-btn
          text
          color="#ff7451"
          @click="requestDeleteComment(comment.no)"
        >삭제
        </v-btn>
      </div>
    </v-card>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import ProfileImgAvatar from '@/components/common/ProfileImgAvatar.vue'
const boardStore = "boardStore"
const authStore = "authStore"

export default {
  name: 'CommentItem',
  props: {
    comment: Object,
  },
  components: {
    ProfileImgAvatar
  },
  data() {
    return {
      date : '',
    }
  },
  computed: {
    ...mapGetters(authStore, ['checkUserInfo']),
  },
  mounted() {
    this.date = this.comment.datetime.replaceAll('-', '.').replace('T', ' ').slice(0, 16);
  },
  methods: {
    ...mapActions(boardStore, ['deleteComment']),
    async requestDeleteComment(commentNo) {
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
.v-btn::before {
  background-color: transparent;
}
</style>
