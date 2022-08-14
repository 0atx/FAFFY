<template>
  <v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2 class="ml-4">게시판 검색 결과</h2>
      <v-btn v-if="boardResult.length != 0" text>인기 순</v-btn>
    </v-row>

    <!-- 게시글 검색 목록 -->
    <v-row>
      <v-col cols="12">
        <v-col style="height:100px; display:flex; justify-content:center; align-items:center;" v-if="!isResultExist" cols="12">
          <h2>검색 결과가 없습니다.</h2>
        </v-col>
        <v-simple-table v-else>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-start" style="width: 15%;">
                  분류
                </th>
                <th class="text-start" style="width: 30%;">
                  제목
                </th>
                <th class="text-start" style="width: 20%;">
                  작성자
                </th>
                <th class="text-start" style="width: 20%;">
                  작성일자
                </th>
                <th class="text-start" style="width: 15%;">
                  조회수
                </th>
              </tr>
            </thead>
            <!--각각의 게시글, 클릭하면 상세조회로 이동-->
            <tbody>
              <tr
                v-for="board in currentPage"
                :key="board.no"
                @click="boardDetail(board.no)"
                style="cursor: pointer"
              >
                <td>{{ typeList[board.category] }}</td>
                <td>{{ board.title }}</td>
                <td>{{ board.user.nickname }}</td>
                <td>{{ board.dateTime.slice(0,4)+'년 '+board.dateTime.slice(5, 7)+'월 '+board.dateTime.slice(8, 10)+'일' }}</td>
                <td>{{ board.hit }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>

    <!-- 페이지네이션 -->
    <v-row v-if="isResultExist">
      <v-col cols="12">
        <v-pagination
          v-model="page"
          circle
          color="#0c0f66"
          :length="totalPages"
          prev-icon="mdi-menu-left"
          next-icon="mdi-menu-right"
          :total-visible="totalVisible">
        </v-pagination>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from 'vuex'
import _ from 'lodash'
const searchStore = "searchStore"

export default {
  name: 'BoardSearchResult',
  data() {
    return {
      page: 1,
      itemsPerPage: 5,
      totalVisible: 7,
      typeList: { 'Free': '자유', 'QnA': '질문', 'Info': '정보' },
    }
  },
  computed: {
    ...mapGetters(searchStore, ['boardResult']),
    totalPages() {
      return this.boardResult.length % this.itemsPerPage > 0 ? parseInt(this.boardResult.length/this.itemsPerPage)+1 : parseInt(this.boardResult.length/this.itemsPerPage)
    },
    // 페이지네이션 - 현재 페이지 게시물
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.boardResult.slice(start, end)
    },
    isResultExist() {
      return !_.isEmpty(this.boardResult)
    }
  },
  methods: {
    boardDetail(boardNo) {
      this.$router.push({ name: 'board-detail', params: { boardNo: boardNo }})
    }
  }
}
</script>

<style>

</style>
