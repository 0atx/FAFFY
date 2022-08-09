<!--
작성자: 류경하
설명: 게시판 글 목록
최종 수정일: 2022.08.03
-->
<template>
  <v-container fluid>
    <h1 class="text-left mb-10">{{ type }} 게시판</h1>
    <v-row align="center" class="mb-5">
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
          hide-details
        ></v-select>
      </v-col>

      <!--검색어 입력-->
      <v-col cols="6">
        <v-text-field
          v-model="keyword"
          label="검색어를 입력하세요"
          append-icon="mdi-magnify"
          outlined
          hide-details
          @keyup.enter="searchKeyword(keyword)"
        ></v-text-field>
      </v-col>
      <v-btn id="searchBtn" class="rounded-lg" col="2" height="56px" @click="searchKeyword(keyword)">검색</v-btn>
      <v-btn id="resetBtn" class="rounded-lg" col="2" height="56px" @click="resetSearch">초기화</v-btn>

    </v-row>

    <v-row>
      <v-col cols="9" align="start">
        <v-btn id="recentBtn" color="#0c0f66" text @click="sortByDate">최신순</v-btn>
        <v-btn id="commentBtn" color="#0c0f66" text @click="sortByComments">답글 많은 순</v-btn>
      </v-col>
      <v-col cols="3" align="end">
        <v-btn @click="createBoard"><v-icon>mdi-pencil</v-icon>글 쓰기</v-btn>
      </v-col>
    </v-row>

    <v-row>
      <v-col cols="12">
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-center">
                  카테고리
                </th>
                <th class="text-center">
                  제목
                </th>
                <th class="text-center">
                  댓글
                </th>
                <th class="text-center">
                  작성자
                </th>
                <th class="text-center">
                  작성일
                </th>
                <th class="text-center">
                  조회수
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
                <td>{{ typeList[board.category] }}</td>
                <td>{{ board.title }}</td>
                <td><v-icon>mdi-comment-processing-outline</v-icon>{{ board.commentCount }}</td>
                <td>{{ board.user.nickname }}</td>
                <td>{{ board.dateTime.slice(0,4)+'년 '+board.dateTime.slice(5, 7)+'월 '+board.dateTime.slice(8, 10)+'일' }}</td>
                <td><v-icon>mdi-eye</v-icon>{{ board.hit }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>
    <!-- 페이지네이션 -->
    <v-row>
      <v-col cols="12">
        <v-pagination
          v-model="page"
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
import { mapGetters, mapActions } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'BoardList',
  data () {
      return {
        page: 1,
        itemsPerPage: 10,
        totalVisible: 7,
        keyword: '',
        items: ['제목', '작성자'],
        searchCategory: '',
        now: this.boardList,
        typeList: { 'Free': '자유', 'QnA': '질문', 'Info': '정보' }
      }
    },
  props: {
    type: String,
  },
  computed: {
    ...mapGetters(boardStore, ['boardList', 'freeBoards', 'qnaBoards', 'infoBoards']),
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
    ...mapActions(boardStore, ['fetchBoards']),
    // 상세조회 페이지 이동
    boardDetail(boardNo) {
      console.log(`${boardNo}번 글로 이동`)
      this.$router.push({ name: "board-detail", params: { boardNo: boardNo }})
    },
    // 최신순 정렬
    sortByDate() {
      this.now.sort((a, b) => {
        return new Date(b.dateTime) - new Date(a.dateTime)
      })
    },
    // 답글 많은 순 정렬
    sortByComments() {
      this.now.sort((a, b) => {
        return b.commentCount - a.commentCount
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
      console.log(this.now)
    },
    // 검색 결과 초기화
    resetSearch() {
      this.keyword = ''
      this.searchCategory = ''
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
        this.now = this.freeBoards
      } else if (this.type === '질문') {
        this.now = this.qnaBoards
      } else if (this.type === '정보') {
        this.now = this.infoBoards
      } else {
        this.now = this.boardList
      }
    },
  },
  created() {
    this.now = this.boardList
  }
}
</script>

<style scoped>
h1 {
  margin-bottom: 10px;
}

button {
  margin-left: 3px;
  margin-right: 3px;
}
#searchBtn {
  background-color: #0c0f66;
  color: white;
}
#resetBtn {
  background-color: #0c0f66;
  color: white;
}
</style>
