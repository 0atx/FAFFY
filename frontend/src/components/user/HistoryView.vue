<template>
  <div id="history">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600">
          <router-link to="/profile/1/user-detail"> Profile </router-link>
        </p>
        <v-icon color="black" class="mb-3"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">History</p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6" style="font-weight: 600">
          <!--방송 기록 참여 | 진행 버튼 들어올 자리-->
          <v-btn>방송 참여</v-btn> |
          <v-btn>방송 진행</v-btn>
        </p>
        <hr />
        <div id="info">
          <!--방송 참여 또는 진행 목록 컴포넌트가 들어올 자리입니다.-->
          <v-data-table
            :headers="headers"
            hide-default-header
            hide-default-footer
            :items="consults"
            :items-per-page="itemsPerPage"
            :page.sync="page"
            @page-count="pageCount = $event"
          ></v-data-table>
          <div class="text-center pt-2">
            <v-pagination
              circle
              color="#0c0f66"
              v-model="page"
              :length="pageCount"
            ></v-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "EditProfileView",
  data() {
    return {
      headers: [
        { text: '방송 제목', align:'start', value:'title'},
        { text: '방송 내용', value:'content'},
        { text: '방송 일자', value:'date'},
      ],
      consults: [
        {
          title: '방송 제목입니다.',
          content: '카테고리 넣고 상세 페이지에는 그 날 입었던 옷들 기록?',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.2',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.3',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.4',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.5',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.6',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.7',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.8',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.9',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.10',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.3',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.4',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.11',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
        {
          title: '방송 제목입니다.12',
          content: '여기 뭐 넣어야 되냐?;;',
          date: '2022-08-01'
        },
      ],

      page: 1,
      pageCount: 0,
      itemsPerPage:10
    }
  }
};
</script>

<style scoped>
#history {
  background-color: #fff;
  padding: 5%;
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

#history div {
  width: 100%;
}

#content {
  height: 300px;
  background-color: #fff;
  padding: 2%;
}

button:before {
  background-color: transparent;
}

#info {
  height: 200px;
}
</style>
