<template>
  <v-container fluid>
    <v-row style="padding-left: 14%; background-color:#0c0f66; color: #fff" class="text-left mt-5 pt-5">
      <h1>{{ boardType }} 게시판</h1>
    </v-row>
    <v-row style="padding-left: 14%; background-color:#0c0f66; color: #fff" class="text-left mb-5 pb-5">
      <h4>{{ content }}</h4>
    </v-row>
    <v-container>
      <BoardDetail />
      <CommentForm />
      <CommentList />
    </v-container>
  </v-container>
</template>

<script>
import { mapActions, mapGetters } from 'vuex'
import BoardDetail from '@/components/board/BoardDetail.vue'
import CommentForm from '@/components/board/CommentForm.vue'
import CommentList from '@/components/board/CommentList.vue'
const boardStore = "boardStore"
const authStore = "authStore"

export default {
  name: 'BoardDeatilView',
  components: {
    BoardDetail,
    CommentForm,
    CommentList,
  },
  data() {
    return {
      boardNo: this.$route.params.boardNo,
      boardType: '',
      content: ''
    }
  },
  computed: {
    ...mapGetters(authStore, ['checkUserInfo']),
    ...mapGetters(boardStore, ['currentBoard']),
  },
  mounted() {
    this.setContent();
  },
  methods: {
    ...mapActions(boardStore, ['fetchBoard', 'deleteBoard']),
    setContent() {
      if(this.currentBoard.category === "Free") {
        this.boardType = "자유"
        this.content = "패션과 관련된 이야기를 자유롭게 나눠보세요."
        return;
      }

      if(this.currentBoard.category === "QnA") {
        this.boardType = "질문"
        this.content = "패션에 대한 궁금증을 질문과 답변을 통해 해결해보세요."
        return;
      }

      if(this.currentBoard.category === "Info") {
        this.boardType = "정보"
        this.content = "최근 유행하는 패션 트렌드를 공유해주세요."
        return;
      }
    },
    // 게시판으로 이동
    moveToBoard() {
      this.$router.push({ name: 'board' })
    },
    // 게시글 삭제
    async requestDeleteBoard(boardNo) {
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
      this.$router.push({ name: 'board-update', params: { boardNo: boardNo }})
    }
  },
  created() {
    this.fetchBoard(this.boardNo)
  }
}
</script>

<style scoped>

</style>
