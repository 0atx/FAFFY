<!--
작성자: 류경하
설명: 게시판 글 목록
최종 수정일: 2022.07.29
-->
<template>
  <div>
    <h3>{{ type }} 게시판</h3>
    <v-btn color="success" @click="sortByDate">최신순</v-btn>
    <v-btn color="primary" @click="sortByComments">답글 많은 순</v-btn>
    <v-btn color="warning">글 쓰기</v-btn>
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
            v-for="article in currentPage"
            :key="article.articleNo"
            @click="articleDetail(article)"
            style="cursor: pointer"
          >
            <td>{{ article.writer }}</td>
            <td>{{ article.comments }}</td>
            <td>{{ article.title }}</td>
            <td>{{ article.created_at }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
    <v-pagination
      v-model="page"
      :length="totalPages"
      prev-icon="mdi-menu-left"
      next-icon="mdi-menu-right"
      :total-visible="totalVisible">
    </v-pagination>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
const boardStore = "boardStore"

export default {
  name: 'ArticleList',
  data () {
      return {
        page: 1,
        itemsPerPage: 5,
        totalVisible: 7,
        now: []
      }
    },
  props: {
    type: String
  },
  computed: {
    ...mapGetters(boardStore, ['freeArticles', 'qnaArticles', 'reviewArticles']),
    totalPages() {
      return this.now.length % this.itemsPerPage > 0 ? parseInt(this.now.length/this.itemsPerPage)+1 : parseInt(this.now.length/this.itemsPerPage)
    },
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.now.slice(start, end)
    },
  },
  methods: {
    // 상세조회 페이지 이동(추후 수정)
    articleDetail(article) {
      console.log(`제목: ${article.title} 글로 이동`)
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
    }
  },
  watch: {
    type() {
      this.page = 1
      if (this.type === '자유') {
        return this.now = this.freeArticles
      } else if (this.type === '질문') {
        return this.now = this.qnaArticles
      } else {
        return this.now = this.reviewArticles
      }
    }
  },
  created() {
    this.now = this.freeArticles
  }
}
</script>

<style>

</style>
