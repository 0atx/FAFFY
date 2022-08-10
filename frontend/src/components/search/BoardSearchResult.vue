<template>
  <v-container class="d-flex flex-column">
    <v-row class="d-flex justify-space-between">
      <h2>게시판 검색 결과</h2>
      <v-btn text>인기 순</v-btn>
    </v-row>

    <!-- 게시글 검색 목록 -->
    <v-row>
      <v-col cols="12">
        <v-simple-table>
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
                :key="board.content.no"
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
      page: 1,
      itemsPerPage: 5,
      totalVisible: 7,
      typeList: { 'Free': '자유', 'QnA': '질문', 'Info': '정보' },
      boards: [
        {
          no: 1,
          category: 'Free',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 1,
          }
        },
        {
          no: 2,
          category: 'QnA',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 2,
          }
        },
        {
          no: 3,
          category: 'Info',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 3,
          },
        },
        {
          no: 4,
          category: 'Free',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 4,
          }
        },
        {
          no: 5,
          category: 'QnA',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 5,
          }
        },
        {
          no: 6,
          category: 'Info',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 6,
          },
        },
        {
          no: 7,
          category: 'Free',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 7,
          }
        },
        {
          no: 8,
          category: 'QnA',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 8,
          }
        },
        {
          no: 9,
          category: 'Info',
          title: '게시글 제목입니다.',
          user: {
            nickname: '별명짓기귀찮다'
            },
          dateTime: '2022-08-01',
          hit: '56',
          content: {
            no: 9,
          },
        },
      ],
    }
  },
  computed: {
    totalPages() {
      return this.boards.length % this.itemsPerPage > 0 ? parseInt(this.boards.length/this.itemsPerPage)+1 : parseInt(this.boards.length/this.itemsPerPage)
    },
    // 페이지네이션 - 현재 페이지 게시물
    currentPage() {
      const start = (this.page-1)*this.itemsPerPage
      const end = start+this.itemsPerPage
      return this.boards.slice(start, end)
    },
  }
}
</script>

<style>

</style>
