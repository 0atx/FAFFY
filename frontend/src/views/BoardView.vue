<template>
  <v-container fluid>
    <v-row style="padding-left: 14%; background-color:#0c0f66; color: #fff" class="text-left mt-5 pt-5">
      <h1>{{ boardType }} 게시판</h1>
    </v-row>
    <v-row style="padding-left: 14%; background-color:#0c0f66; color: #fff" class="text-left mb-5 pb-5">
      <h4>{{ content }}</h4>
    </v-row>
    <v-container>
      <v-row>
        <v-col
        cols="3">
          <SideBar @changeBoardType="changeType"/>
        </v-col>
        <v-col
        cols="9">
          <BoardList :type="boardType" />
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import SideBar from '@/components/board/SideBar.vue'
import BoardList from '@/components/board/BoardList.vue'
import { mapActions } from 'vuex'
const boardStore = "boardStore";

export default {
  name: 'BoardView',
  components: {
    SideBar,
    BoardList,
  },
  data() {
    return {
      boardType: '전체',
      content: '회원들과 자유롭게 소통해보세요.'
    }
  },
  methods: {
    ...mapActions(boardStore, ['fetchBoardList']),
    // 게시판 카테고리 변경
    changeType(type) {
      this.boardType = type

      if(this.boardType === "전체") {
        this.content = "회원들과 자유롭게 소통해보세요."
        return;
      }

      if(this.boardType === "자유") {
        this.content = "패션과 관련된 이야기를 자유롭게 나눠보세요."
        return;
      }

      if(this.boardType === "질문") {
        this.content = "패션에 대한 궁금증을 질문과 답변을 통해 해결해보세요."
        return;
      }

      if(this.boardType === "정보") {
        this.content = "최근 유행하는 패션 트렌드를 공유해보세요."
        return;
      }
    },
  },
  created() {
    this.fetchBoardList()
  },
  mounted() {
    window.scrollTo(0, 0);
  }
}
</script>

<style scoped>

</style>
