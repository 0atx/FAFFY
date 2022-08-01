<template>
  <div id="board">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600">
          <router-link to="/profile/1/user-detail"> Profile </router-link>
        </p>
        <v-icon color="black" class="mb-3"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">Board</p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6">
          <v-tabs color="0c0f66">
            <v-tab :id="selected === 1? 'selectedBtn' : 'selectBtn'" @click="selected = 1">Q&A</v-tab>
            <v-tab :id="selected === 2? 'selectedBtn' : 'selectBtn'" @click="selected = 2">자유</v-tab>
            <v-tab :id="selected === 3 ? 'selectedBtn' : 'selectBtn'" @click="selected = 3">정보</v-tab>
          </v-tabs>
        </p>

        <div>
          <!-- 카테고리별 작성 게시글 리스트 selected로 카테고리 구분해서 출력할 것 -->
          <v-data-table
            id="table"
            :headers="headers"
            hide-default-footer
            :items="boards"
            :items-per-page="itemsPerPage"
            :page.sync="page"
            @page-count="pageCount = $event"
          ></v-data-table>

          <!-- pagination -->
          <div class="text-center pt-4">
            <v-pagination
              circle
              color="#0c0f66"
              v-model="page"
              :length="pageCount"
              :total-visible="5"
            ></v-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BoardView",
  data() {
    return {
      headers: [
        { text: '게시글 제목', align:'start', value:'title'},
        { text: '댓글 수', value:'comments'},
        { text: '작성 일자', value:'date'},
        { text: '조회수', value:'hit'},
      ],
      // 임시 게시글 기록, DB에서 받아와서 넘겨줘야 함
      boards: [
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
        {
          title: '게시글 제목입니다.',
          comments: '[13]',
          date: '2022-08-01',
          hit: '56'
        },
      ],

      // pagination 관련 변수
      page: 1,
      pageCount: 0,
      itemsPerPage:10,

      // 카테고리 판별하는 변수 Q&A : 1, 자유 : 2, 정보 : 3
      selected: 1,
    }
  }
};
</script>

<style scoped>
#board {
  background-color: #fff;
  padding: 5%;
  padding-bottom: 2%;
  text-align: left;
  height: 100%;
  display: flex;
  justify-content: start;
  align-items: flex-start;
  cursor: default;
}

#route {
  display: flex;
  justify-content: start;
}

a {
  color: black;
  text-decoration: none;
}

#board div {
  width: 100%;
}

#content {
  background-color: #fff;
  padding: 2%;
}

#selectBtn {
  background-color: #fff;
  color: #0c0f66;
  font-weight: 600;
  font-size: 16px;
}

#selectedBtn {
  background-color: #0c0f66;
  color: #fff;
  font-weight: 600;
  font-size: 16px;
}

#table {
  height: 520px;
}

button:before {
  background-color: transparent;
}
</style>
