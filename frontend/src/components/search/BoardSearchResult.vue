<template>
  <v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2 class="ml-4">게시판 검색 결과</h2>
      <v-col
        class="d-flex"
        cols="2"
      >
        <v-select
          :items="items"
          label="정렬 기준"
          dense
          solo
          v-model="sortBy"
        ></v-select>
      </v-col>
    </v-row>

    <!-- 게시글 검색 목록 -->
    <v-row>
      <v-col cols="12">
        <v-col style="height:350px; display:flex; justify-content:center; align-items:center;" v-if="!isResultExist" cols="12">
          <div style="text-align:center;">
            <v-icon color="#333" large block> mdi-clipboard-text-off-outline </v-icon>
            <h2>'{{ keyword }}'에 대한 검색 결과가 없습니다.</h2>
          </div>
        </v-col>
        <v-simple-table v-else>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-start" style="width: 15%;">
                  카테고리
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
  props: {
    keyword: String,
  },
  data() {
    return {
      page: 1,
      itemsPerPage: 5,
      totalVisible: 7,
      typeList: { 'Free': '자유', 'QnA': '질문', 'Info': '정보' },
      items: ['카테고리', '제목', '작성일자', '조회수'],
      sortBy: '',
      type: { '카테고리': 'category', '제목': 'title', '작성일자': 'dateTime', '조회수': 'hit' }
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
    },
    sortResult(sortBy) {
      this.boardResult.sort((a, b) => {
        if (b[this.type[sortBy]] > a[this.type[sortBy]]) {
          return 1
        } else if (b[this.type[sortBy]] < a[this.type[sortBy]]) {
          return -1
        } else {
          return 0
        }
      })
    }
  },
  watch: {
    sortBy() {
      this.sortResult(this.sortBy)
    }
  }
}
</script>

<style>

</style>
