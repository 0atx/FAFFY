<!--
작성자: 류경하
설명: 게시판 글 목록
최종 수정일: 2022.08.10
최종 수정자: 박윤하
-->
<template>
  <v-container>
    <v-row>
      <v-col cols="3" class="pr-0" align="start">
        <v-btn id="recentBtn" class="ml-2 pl-2 pr-2" color="#0c0f66" text @click="sortByDate">최신 순</v-btn>
        <v-btn id="commentBtn" class="ml-0 pl-2 pr-2" color="#0c0f66" text @click="sortByComments">답글 순</v-btn>
      </v-col>
      <v-col cols="7" class="pl-0" style="display:flex;" align="start">
        <!--검색 기준 선택(작성자 or 제목)-->
        <v-select
          style="width:30%; margin-right:10px;"
          :items="items"
          color="#0c0f66"
          label="검색"
          v-model="searchCategory"
          dense
          outlined
          hide-details
        ></v-select>

        <!--검색어 입력-->
        <v-text-field
          style="width:65%;"
          color="#0c0f66"
          v-model="keyword"
          placeholder="검색어를 입력하세요"
          append-icon="mdi-magnify"
          dense
          outlined
          hide-details
          @keyup.enter="searchKeyword(keyword)"
          @click:append="searchKeyword(keyword)"
        ></v-text-field>
      <v-btn :ripple="false" elevation="0" id="searchBtn" style="width:10%;" @click="searchKeyword(keyword)">검색</v-btn>
      <v-btn :ripple="false" elevation="0" id="resetBtn" style="width:15%;" @click="resetSearch">초기화</v-btn>
      </v-col>
      <v-col cols="2" align="end">
        <v-btn :ripple="false" elevation="0" id="writeBtn" @click="createBoard"><v-icon small class="mr-1">mdi-pencil</v-icon> 글 쓰기</v-btn>
      </v-col>
    </v-row>

    <v-row id="table">
      <v-col cols="12">
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr>
                <th class="text-center" width="12%">
                  분류
                </th>
                <th class="text-center">
                  제목
                </th>
                <th class="text-center" width="8%">
                  댓글
                </th>
                <th class="text-center" width="15%">
                  작성자
                </th>
                <th class="text-center" width="10%">
                  작성일
                </th>
                <th class="text-center" width="10%">
                  조회수
                </th>
              </tr>
            </thead>
            <!--각각의 게시글, 클릭하면 상세조회로 이동-->
            <tbody>
              <tr v-if="now.length == 0" id="noneTr" style="height:400px;">
                <td colspan="6" style="text-align:center;">
                  <v-icon color="#333" large block> mdi-clipboard-text-off-outline </v-icon>
                  <h4>작성된 게시글이 없습니다.</h4>
                </td>
              </tr>
              <tr
                v-else
                v-for="board in currentPage"
                :key="board.content.no"
                @click="boardDetail(board.no)"
                style="cursor: pointer"
              >
                <td>{{ typeList[board.category] }}</td>
                <td>{{ board.title }}</td>
                <td><v-icon small> mdi-comment-processing-outline </v-icon> {{ board.commentCount }}</td>
                <td>{{ board.user.nickname }}</td>
                <td>{{ board.dateTime.replaceAll('-', '.').slice(0,10) }}</td>
                <td><v-icon small> mdi-eye </v-icon> {{ board.hit }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
      </v-col>
    </v-row>
    <!-- 페이지네이션 -->
    <v-row v-if="now.length != 0">
      <v-col cols="12">
        <v-pagination
          circle
          color="#0c0f66"
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
import { mapGetters } from 'vuex'
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
        this.$dialog.message.info('검색 기준을 선택하세요', {
          position: "top",
          timeout: 2000,
          color: "#ff7451",
        });
      }
      console.log(this.now)
    },
    // 검색 결과 초기화
    resetSearch() {
      this.keyword = ''
      this.searchCategory = ''
      this.page = 1
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
  margin-left: 10px;
  height: 40px !important;
}

#recentBtn::before, #commentBtn::before {
  background-color: transparent;
}

#searchBtn {
  background-color: #0c0f66;
  color: white;
}

#resetBtn {
  background-color: #ff7451;
  color: white;
}

#writeBtn {
  background-color: #f0f0f0;
  color: black;
}

#table {
  height: 530px;
}

#noneTr:hover {
  background-color: transparent;
}
</style>
