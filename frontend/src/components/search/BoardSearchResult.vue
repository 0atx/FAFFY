<template>
  <v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2>게시판 검색 결과</h2>
      <v-btn text>인기 순</v-btn>
    </v-row>

    <!-- 게시글 검색 목록 -->
    <v-row>
      <v-col cols="12">
        <v-data-table
          :headers="headers"
          :items="boards"
          :items-per-page="5"
          hide-default-footer
          class="elevation-1">
        </v-data-table>
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
export default {
  name: 'BoardSearchResult',
  data() {
    return {
      headers: [
        { text: '분류', align:'start', value:'category', width: '10%'},
        { text: '게시글 제목', align: 'start',value:'title', width: '50%'},
        { text: '작성자', align: 'start',value:'writer', width: '15%'},
        { text: '작성 일자', align: 'start',value:'date', width: '15%'},
        { text: '조회수', align: 'center',value:'hit', width: '10%'},
      ],
      boards: [
        {
          category: '자유',
          title: '게시글 제목입니다.',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
        {
          category: '자유',
          title: '게시글 제목입니다.22',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
        {
          category: '자유',
          title: '게시글 제목입니다.33',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
        {
          category: '자유',
          title: '게시글 제목입니다.44',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
        {
          category: '자유',
          title: '게시글 제목입니다.55',
          writer: '별명짓기귀찮다',
          date: '2022-08-01',
          hit: '56'
        },
      ],
    }
  }
}
</script>

<style>

</style>
