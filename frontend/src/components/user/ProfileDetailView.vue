<!--
작성자 : 박윤하
프로필 뷰 - 우측 세부 프로필 정보
-->
<template>
  <div id="profileDetail">
    <div>
      <div id="route">
        <p class="text-h6" style="font-weight: 600">Profile</p>
      </div>
      <div id="content" class="mt-4">
        <p class="text-h6" style="font-weight: 600">정보</p>
        <hr />
        <div id="info">
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
          <!-- 방송 기록 리스트 부분입니다. 추후 방송 기록 리스트 컴포넌트 구현 후
          추가할 예정입니다. <br />ex) 카테고리(참여, 진행) | 방송 제목 ㅁ사진
          갯수 or 방송 일자 -->
          <div v-if="isHistoryExist">
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-start">
                      카테고리
                    </th>
                    <th class="text-start">
                      제목
                    </th>
                    <th class="text-start">
                      진행자
                    </th>
                    <th class="text-start">
                      방송일
                    </th>
                  </tr>
                </thead>
                <!--각각의 게시글, 클릭하면 상세조회로 이동-->
                <tbody>
                  <tr
                    v-for="consulting in consultingHistory.slice(0, 5)"
                    :key="consulting.consulting_no"
                    style="cursor: pointer"
                    @click="historyDetail(consulting.consulting_no)"
                  >
                    <td>{{ checkUserInfo.nickname === consulting.consultant ? "진행" : "참여" }}</td>
                    <td>{{ consulting.title }}</td>
                    <td>{{ consulting.consultant }}</td>
                    <td>{{ consulting.date }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
          <div v-else>
            방송 이력이 없습니다.
          </div>
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
          <!-- 게시글 리스트 부분입니다. 추후 게시글 리스트 컴포넌트 구현 후 추가할
          예정입니다. <br />ex) 카테고리(Q&A, 자유) | 게시글 제목 ㅁ댓글 갯수 or
          작성 일자 -->
          <div v-if="isBoardExist">
            <v-simple-table>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-start">
                      카테고리
                    </th>
                    <th class="text-start">
                      제목
                    </th>
                    <th class="text-start">
                      작성일
                    </th>
                    <th class="text-start">
                      조회수
                    </th>
                  </tr>
                </thead>
                <!--각각의 게시글, 클릭하면 상세조회로 이동-->
                <tbody>
                  <tr
                    v-for="board in userBoardList.slice(0, 5)"
                    :key="board.no"
                    style="cursor: pointer"
                    @click="boardDetail(board.no)"
                  >
                    <td>{{ board.category }}</td>
                    <td>{{ board.title }}</td>
                    <td>{{ board.date }}</td>
                    <td>{{ board.hit }}</td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </div>
          <div v-else>
            작성 게시글이 없습니다.
          </div>
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
  computed: {
    ...mapState(profileStore, ["userProfile"]),
    ...mapGetters(profileStore, ["userBoardList", "participatedList","consultingList"]),
    ...mapGetters(authStore, ['checkUserInfo']),
    consultingHistory() {
      return [...this.participatedList, ...this.consultingList].sort((a, b) => {
        return b.date - a.date
      })
    },
    isHistoryExist() {
      return !_.isEmpty(this.consultingHistory)
    },
    isBoardExist() {
      return !_.isEmpty(this.userBoardList)
    }
  },
  data() {
    return {};
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
  height: 300px;
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
