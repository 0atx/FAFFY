<!--
작성자 : 박윤하
프로필 뷰 - 방송 기록 목록 페이지
-->
<template>
  <div id="history">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600">
          <router-link :to="{ name: 'profile' }"> Profile </router-link>
        </p>
        <v-icon color="black" class="mb-3"> mdi-chevron-right </v-icon>
        <p class="text-h6" style="font-weight: 600">History</p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6">
          <!--방송 참여 | 진행 기록 탭-->
          <v-tabs color="0c0f66">
            <v-tab
              :id="selected ? 'selectedBtn' : 'selectBtn'"
              @click="selected = true"
              >참여 기록</v-tab
            >
            <v-tab
              :id="!selected ? 'selectedBtn' : 'selectBtn'"
              @click="selected = false"
              >진행 기록</v-tab
            >
          </v-tabs>
        </p>

        <div>
          <!--방송 참여 또는 진행 목록 리스트 selected로 참여인지 진행인지 구분해서 출력할 것 -->
          <v-data-table
            id="table"
            :headers="headers"
            hide-default-footer
            :items="consults"
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
  name: "HistoryView",
  data() {
    return {
      headers: [
        { text: "방송 제목", align: "start", value: "title" },
        { text: "방송 소개", value: "intro" },
        { text: "방송 진행자", value: "consultant" },
        { text: "방송 일자", value: "date" },
      ],
      // 임시 방송 기록, DB에서 받아와서 넘겨줘야 함
      consults: [
        {
          title: "방송 제목입니다.",
          intro: "카테고리 넣고 상세 페이지에는 그 날 입었던 옷들 기록?",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "제목도 10자 제한(최대 50자)",
          intro: "30자 출력하게 해야함(최대 300자)",
          consultant: "별명도 10자?",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.3",
          intro: "if consults.intro.length <= 30 그냥 출력",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.4",
          intro: 'else consult.intro.substring(0, 30) + "..." 해서 출력',
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.5",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.6",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.7",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.8",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.9",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.10",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.11",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.12",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.13",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
        {
          title: "방송 제목입니다.14",
          intro: "여기 뭐 넣어야 되냐?;;",
          consultant: "별명짓기귀찮다",
          date: "2022-08-01",
        },
      ],

      // pagination 관련 변수
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,

      // 참여 기록인지 진행 기록인지 판별하는 변수 참여 : true, 진행 : false
      selected: true,
    };
  },
};
</script>

<style scoped>
#history {
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

#history div {
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
