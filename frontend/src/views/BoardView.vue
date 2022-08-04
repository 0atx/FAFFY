<template>
  <v-container class="grey lighten-5" fluid>
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
      boardType: '자유',
    }
  },
  methods: {
    ...mapActions(boardStore, ['fetchBoardList']),
    // 게시판 카테고리 변경
    changeType(type) {
      this.boardType = type
      console.log(type)
    },
  },
  created() {
    this.fetchBoardList()
  }
}
</script>

<style>

</style>
