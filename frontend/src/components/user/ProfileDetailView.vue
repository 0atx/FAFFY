<!--
작성자 : 박윤하
프로필 뷰 - 우측 세부 프로필 정보
-->
<template>
  <div id="profileDetail">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600"> 정보 </p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6" style="font-weight: 600"> 자기소개 </p>
        <hr />
        <div id="info" style="white-space: pre-wrap;">
          {{ userProfile.introduce }}
        </div>
      </div>
      <div id="content">
        <p class="text-h6" style="font-weight: 600">
          방송 기록
          <v-btn
            id="moreBtn"
            class="mx-2 mb-1"
            elevation="0"
            icon
            small
            color="gray"
            :ripple="false"
            :to="{ name: 'user-history' }"
            :disabled="!isHistoryExist"
          >
            <v-icon> mdi-plus </v-icon>
          </v-btn>
        </p>
        <hr />
        <div id="history">
          <!-- 방송 기록 리스트 -->
          <div v-if="isHistoryExist">
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-start">
                      분류
                    </th>
                    <th class="text-start">
                      방송 제목
                    </th>
                    <th class="text-start">
                      방송 진행자
                    </th>
                    <th class="text-start">
                      방송 일자
                    </th>
                  </tr>
                </thead>
                <!--각각의 게시글, 클릭하면 상세조회로 이동-->
                <tbody>
                  <tr
                    v-for="(consulting, i) in consultingHistory.slice(0, 4)"
                    :key="i"
                    style="cursor: pointer"
                    @click="historyDetail(consulting.consulting_no)"
                  >
                    <td>{{ userProfile.nickname === consulting.consultant ? "진행" : "참여" }}</td>
                    <td>{{ consulting.title }}</td>
                    <td>{{ consulting.consultant }}</td>
                    <td>{{ consulting.date.replaceAll('-', '.').slice(0, 10) }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
          <v-col v-else style="height:300px; display:flex; justify-content:center; align-items:center;" cols="12">
            <div style="text-align:center;">
              <v-icon color="#333" large block> mdi-clipboard-text-off-outline </v-icon>
              <h4>방송 기록이 없습니다.</h4>
            </div>
          </v-col>
        </div>
      </div>
      <div id="content">
        <p class="text-h6" style="font-weight: 600">
          게시글
          <v-btn
            id="moreBtn"
            class="mx-2 mb-1"
            elevation="0"
            icon
            small
            color="gray"
            :ripple="false"
            :to="{ name: 'user-board' }"
            :disabled="!isBoardExist"
          >
            <v-icon> mdi-plus </v-icon>
          </v-btn>
        </p>
        <hr />
        <div id="board">
          <!-- 게시글 리스트 -->
          <div v-if="isBoardExist">
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-start" width="15%">
                      분류
                    </th>
                    <th class="text-start" width="50%">
                      제목
                    </th>
                    <th class="text-start" width="10%">
                      댓글
                    </th>
                    <th class="text-start" width="15%">
                      작성 일자
                    </th>
                    <th class="text-start" width="10%">
                      조회수
                    </th>
                  </tr>
                </thead>
                <!--각각의 게시글, 클릭하면 상세조회로 이동-->
                <tbody>
                  <tr
                    v-for="(board, i) in userBoardList.slice(0, 4)"
                    :key="i"
                    style="cursor: pointer"
                    @click="boardDetail(board.no)"
                  >
                    <td>{{ type[board.category] }}</td>
                    <td>{{ board.title }}</td>
                    <td><v-icon small> mdi-comment-processing-outline </v-icon> {{ board.commentCount }}</td>
                    <td>{{ board.dateTime.replaceAll('-', '.').slice(0, 10) }}</td>
                    <td><v-icon small> mdi-eye </v-icon> {{ board.hit }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
          <v-col v-else style="height:300px; display:flex; justify-content:center; align-items:center;" cols="12">
            <div style="text-align:center;">
              <v-icon color="#333" large block> mdi-clipboard-text-off-outline </v-icon>
              <h4>작성한 게시글이 없습니다.</h4>
            </div>
          </v-col>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import _ from 'lodash'
const profileStore = "profileStore";
const authStore = "authStore";

export default {
  name: "ProfileDetailView",
  data() {
    return {
      type: {'Free': '자유', 'QnA': '질문', 'Info': '정보'},
    }
  },
  computed: {
    ...mapState(profileStore, ["userProfile"]),
    ...mapGetters(profileStore, ["userBoardList", "participatedList", "consultingList", "consultingHistoryList"]),
    ...mapGetters(authStore, ['checkUserInfo']),
    consultingHistory() {
      // return [...this.participatedList, ...this.consultingList].sort((a, b) => {
      //   return b.date - a.date
      // })
      return [...this.consultingHistoryList].sort((a,b) => {
        return b.date - a.date;
      })
    },
    isHistoryExist() {
      return !_.isEmpty(this.consultingHistory)
    },
    isBoardExist() {
      return !_.isEmpty(this.userBoardList)
    }
  },
  methods: {
    historyDetail(consulting_no) {
      this.$router.push({ name: 'user-history-detail', params: { consultNo: consulting_no }})
    },
    boardDetail(boardNo) {
      this.$router.push({ name: 'board-detail', params: { boardNo: boardNo }})
    }
  },
  metaInfo() {
    return {
      meta: [
        { charset: "utf-8" },
        { name: "viewport", content: "width=device-width, initial-scale=1" },
      ],
    };
  },
};
</script>

<style scoped>
#profileDetail {
  background-color: #fff;
  padding: 5%;
  text-align: left;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  cursor: default;
}

#profileDetail div {
  width: 100%;
}

#content {
  background-color: #fff;
  padding: 2%;
}

#info {
  height: 200px;
}

#history {
  height: 300px;
}

#board {
  height: 300px;
}

#moreBtn::before {
  background-color: transparent;
}

#moreBtn i:hover {
  transform: scale(1.5);
}
</style>
