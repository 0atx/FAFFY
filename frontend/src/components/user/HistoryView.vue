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
            v-if="(selected && participatedList.length != 0) || (!selected && consultingList.length != 0)"
            :items="selected? participatedList : consultingList"
            :items-per-page="itemsPerPage"
            :page.sync="page"
            @page-count="pageCount = $event"
            @click:row="historyDetail"
            style="cursor: pointer"
          ></v-data-table>
          <v-col v-else style="height:600px; display:flex; justify-content:center; align-items:center;" cols="12">
            <div style="text-align:center;">
              <v-icon color="#333" large block> mdi-clipboard-text-off-outline </v-icon>
              <h4>{{ nodata }}</h4>
            </div>
          </v-col>

          <!-- pagination -->
          <div class="text-center pt-4">
            <v-pagination
              v-if="(selected && participatedList.length != 0) || (!selected && consultingList.length != 0)"
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
import { mapGetters } from 'vuex';
const profileStore = "profileStore";

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
      // pagination 관련 변수
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,

      // 참여 기록인지 진행 기록인지 판별하는 변수 참여 : true, 진행 : false
      selected: true,
      nodata: '방송 참여 기록이 없습니다.'
    };
  },
  computed: {
    ...mapGetters(profileStore, ['participatedList', 'consultingList'])
  },
  methods: {
    historyDetail (e) {
      console.log(`${e.consulting_no}번 글의 상세 history로 이동합니다.`)
      this.$router.push({ name: 'user-history-detail', params: { consultNo: e.consulting_no } })
    }
  },
  watch: {
    selected() {
      this.page = 1
      if(this.selected) {
        this.nodata = '방송 참여 기록이 없습니다.'
      } else {
        this.nodata = '방송 진행 기록이 없습니다.'
      }
    }
  }
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
