<!--
작성자: 류경하
설명: 게시판 글 목록
최종 수정일: 2022.08.03
-->
<template>
  <v-container fluid>
    <v-row align="center">
      <!--검색 기준 선택(작성자 or 제목)-->
      <v-col
        class="d-flex"
        cols="2"
      >
        <v-select
          :items="items"
          label="검색"
          v-model="searchCategory"
          outlined
        ></v-select>
      </v-col>

      <!--검색어 입력-->
      <v-col cols="6">
        <v-text-field
          v-model="keyword"
          label="검색어를 입력하세요"
          append-icon="mdi-magnify"
          @keyup.enter="searchKeyword(keyword)"
        ></v-text-field>
      </v-col>
      <v-btn col="4" color="success" @click="resetSearch">검색 결과 초기화</v-btn>

    </v-row>
    <h3>{{ type }} 게시판</h3>
    <v-btn color="success" @click="sortByDate">최신순</v-btn>
    <v-btn color="primary" @click="sortByComments">답글 많은 순</v-btn>
    <v-btn color="warning" @click="createBoard">글 쓰기</v-btn>
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr>
            <th class="text-center">
              작성자
            </th>
            <!--추후에 카테고리 대신 댓글 수로 대체 예정-->
            <th class="text-center">
              댓글 수
            </th>
            <th class="text-center">
              제목
            </th>
            <th class="text-center">
              작성일
            </th>
          </tr>
        </thead>
        <!--각각의 게시글, 클릭하면 상세조회로 이동-->
        <tbody>
          <tr
            v-for="board in currentPage"
            :key="board.content.no"
            @click="boardDetail(board.no)"
            style="cursor: pointer"
          >
            <td>{{ board.user.nickname }}</td>
            <td>{{ board.commentCount }}</td>
            <td>{{ board.title }}</td>
            <td>{{ board.dateTime }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <!-- 페이지네이션 -->
    <v-pagination
      v-model="page"
      :length="totalPages"
      prev-icon="mdi-menu-left"
      next-icon="mdi-menu-right"
      :total-visible="totalVisible">
    </v-pagination>
  </v-container>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'BoardList',
  data () {
      return {
        page: 1,
        itemsPerPage: 10,
        totalVisible: 7,
        now: [],
        keyword: '',
        items: ['제목', '작성자'],
        searchCategory: ''
      }
    },
  props: {
    type: String,
  },
  computed: {
    ...mapGetters(boardStore, ['freeBoards', 'qnaBoards', 'infoBoards']),
    // 페이지네이션 - 전체 페이지
    totalPages() {
      return this.now.length % this.itemsPerPage > 0 ? parseInt(this.now.length/this.itemsPerPage)+1 : parseInt(this.now.length/this.itemsPerPage)
    },
    // 페이지네이션 - 현재 페이지 게시물
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.now.slice(start, end)
    },
  },
  methods: {
    ...mapActions(['fetchBoards']),
    // 상세조회 페이지 이동
    boardDetail(boardNo) {
      console.log(`${boardNo}번 글로 이동`)
      this.$router.push({ name: "board-detail", params: { boardNo: boardNo }})
    },
    // 최신순 정렬
    sortByDate() {
      this.now.sort((a, b) => {
        return new Date(b.created_at) - new Date(a.created_at)
      })
    },
    // 답글 많은 순 정렬
    sortByComments() {
      this.now.sort((a, b) => {
        return b.comments - a.comments
      })
    },
    // 검색 카테고리 별 정렬
    searchKeyword(keyword) {
      this.keyword = keyword
      console.log(keyword)
      if (this.searchCategory === '작성자') {
          this.now = this.now.filter(board => board.user.nickname.includes(this.keyword))
      } else if (this.searchCategory === '제목') {
        this.now = this.now.filter(board => board.title.includes(this.keyword))
      } else {
        alert('검색 기준을 선택하세요')
      }
    },
    // 검색 결과 초기화
    resetSearch() {
      this.keyword = ''
      this.searchCategory = ''
      if (this.type === '자유') {
        return this.now = this.freeBoards
      } else if (this.type === '질문') {
        return this.now = this.qnaBoards
      } else {
        return this.now = this.infoBoards
      }
    },
    // 게시글 생성
    createBoard() {
      this.$router.push({ name: 'board-new' })
    },
  },
  watch: {
    // 게시판 타입이 변경될 때
    type() {
      this.keyword = ''
      this.page = 1
      this.searchCategory = ''
      if (this.type === '자유') {
        return this.now = this.freeBoards
      } else if (this.type === '질문') {
        return this.now = this.qnaBoards
      } else {
        return this.now = this.infoBoards
      }
    },
  },
  created() {
    this.now = this.freeBoards
  }
}
</script>

<style>

</style>
